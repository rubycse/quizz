package net.quizz.quiz.web.controller;

import net.quizz.auth.domain.User;
import net.quizz.common.service.AuthService;
import net.quizz.common.utils.DateUtils;
import net.quizz.quiz.domain.quiz.Quiz;
import net.quizz.quiz.domain.template.Publication;
import net.quizz.quiz.domain.template.PublishFor;
import net.quizz.quiz.domain.template.QuizTemplate;
import net.quizz.quiz.repository.QuizDao;
import net.quizz.quiz.service.QuizAccessManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author lutfun
 * @since 10/4/16
 */

@Controller
@SessionAttributes("publication")
@RequestMapping(path = "/publication")
public class PublicationController {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private AuthService authService;

    @Autowired
    private QuizAccessManager quizAccessManager;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Integer.class, null, new CustomNumberEditor(Integer.class, true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT_12H), true));
    }

    @RequestMapping(path = "/publish", method = RequestMethod.GET)
    public String show(ModelMap model, @RequestParam int quizId) {
        QuizTemplate quizTemplate = quizDao.getQuizTemplate(quizId);
        quizAccessManager.canEdit(quizTemplate);
        Publication publication = quizDao.getPublicationByQuiz(quizTemplate);

        if (publication == null) {
            publication = new Publication();
            publication.setQuizTemplate(quizTemplate);
            publication.setPublishedBy(authService.getUser());
        }

        setupReferenceData(model, publication);

        return "publication/publish";
    }

    @RequestMapping(path = "/publish", method = RequestMethod.POST)
    public String publish(@ModelAttribute Publication publication) {
        QuizTemplate quizTemplate = publication.getQuizTemplate();
        quizAccessManager.canEdit(quizTemplate);
        publication.setPublishedOn(new Date());

        if (!quizTemplate.isPublished()) {
            quizTemplate.setPublished(true);
            quizDao.save(quizTemplate);
        }
        quizDao.save(publication);

        return "redirect:/quiz/template/myTemplates";
    }

    @RequestMapping(path = "/sharedWithMe", method = RequestMethod.GET)
    public String sharedWithMe(ModelMap model) {
        User user = authService.getUser();
        List<Publication> publications = quizDao.getPublicationsSharedWithMe(user);

        model.put("publications", publications);
        model.put("sharedWithMe", true);

        return "publication/list";
    }

    @RequestMapping(path = "/public", method = RequestMethod.GET)
    public String sharedW(ModelMap model) {

        model.put("publications", quizDao.getAllPublicPublications());

        return "publication/list";
    }

    @RequestMapping(path = "/show", method = RequestMethod.GET)
    public String show(@RequestParam int id, ModelMap model, RedirectAttributes redirectAttributes) {
        Publication publication = quizDao.getPublication(id);

        User user = authService.getUser();
        boolean createdByUser = publication.getQuizTemplate().getCreatedBy().getId() == user.getId();
        if (createdByUser) {
            redirectAttributes.addAttribute("id", publication.getQuizTemplate().getId());
            return "redirect:/quiz/template/show";
        }

        Quiz quiz = quizDao.getQuiz(publication, user);
        if (quiz != null && (quiz.isExpired() || quiz.isCompleted())) {
            redirectAttributes.addAttribute("quizId", quiz.getId());
            return "redirect:/quiz/quiz/show";
        }

        model.put("publication", publication);
        model.put("datePattern", DateUtils.DATE_TIME_FORMAT_READABLE);

        return "publication/show";
    }

    private void setupReferenceData(ModelMap model, Publication publication) {
        model.put("publication", publication);
        model.put("publishOptions", PublishFor.values());
        model.put("contacts", quizDao.getUserContacts(authService.getUser()));
        model.put("datePattern", DateUtils.DATE_TIME_FORMAT_12H);
    }
}
