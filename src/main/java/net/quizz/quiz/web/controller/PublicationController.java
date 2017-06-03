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
    public String show(ModelMap model,
                       @RequestParam(defaultValue = "0") int quizTemplateId,
                       @RequestParam(defaultValue = "0") int id) {

        Publication publication;
        if (id == 0) {
            publication = new Publication();
            publication.setQuizTemplate(quizDao.getQuizTemplate(quizTemplateId));
            publication.setPublishedBy(authService.getUser());
        } else {
            publication = quizDao.getPublication(id);
        }

        quizAccessManager.canPublish(publication.getQuizTemplate());

        setupReferenceData(model, publication);

        return "publication/publish";
    }

    @RequestMapping(path = "/publish", method = RequestMethod.POST)
    public String publish(@ModelAttribute Publication publication,
                          RedirectAttributes redirectAttributes) {

        quizAccessManager.canPublish(publication.getQuizTemplate());

        publication.setPublishedOn(new Date());
        quizDao.save(publication);

        redirectAttributes.addAttribute("quizTemplateId", publication.getQuizTemplate().getId());

        return "redirect:quizPublications";
    }

    @RequestMapping(path = "/quizPublications", method = RequestMethod.GET)
    public String list(@RequestParam int quizTemplateId, ModelMap model) {

        QuizTemplate quizTemplate = quizDao.getQuizTemplate(quizTemplateId);
        quizAccessManager.canPublish(quizTemplate);

        List<Publication> publications = quizDao.getPublications(quizTemplate);

        model.put("publications", publications);
        model.put("quizTemplate", quizTemplate);
        model.put("datePattern", DateUtils.DATE_TIME_FORMAT_12H);

        return "publication/quizPublication";
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
    public String publicList(ModelMap model) {

        model.put("publications", quizDao.getAllPublicPublications());

        return "publication/list";
    }

    @RequestMapping(path = "/show", method = RequestMethod.GET)
    public String show(@RequestParam int id, ModelMap model, RedirectAttributes redirectAttributes) {

        Publication publication = quizDao.getPublication(id);
        User user = authService.getUser();
        boolean createdByUser = publication.getQuizTemplate().getCreatedBy().getId() == user.getId();

        if (createdByUser) {
            redirectAttributes.addAttribute("id", id);
            return "redirect:publish";
        }

        model.put("quiz", quizDao.getQuiz(publication, user));
        model.put("publication", publication);
        model.put("datePattern", DateUtils.DATE_TIME_FORMAT_READABLE);

        return "publication/show";
    }

    private void setupReferenceData(ModelMap model, Publication publication) {
        model.put("publication", publication);
        model.put("publishOptions", PublishFor.values());
        model.put("contacts", quizDao.getUserContacts(authService.getUser()));
        model.put("studentGroups", quizDao.getStudentGroups(authService.getUser()));
        model.put("datePattern", DateUtils.DATE_TIME_FORMAT_12H);
    }
}
