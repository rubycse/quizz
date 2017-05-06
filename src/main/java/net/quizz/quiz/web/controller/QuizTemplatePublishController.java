package net.quizz.quiz.web.controller;

import net.quizz.common.service.AuthService;
import net.quizz.quiz.domain.template.Publication;
import net.quizz.quiz.domain.template.PublishFor;
import net.quizz.quiz.domain.template.QuizTemplate;
import net.quizz.quiz.repository.QuizDao;
import net.quizz.quiz.service.QuizAccessManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Set;

/**
 * @author lutfun
 * @since 10/4/16
 */

@Controller
@SessionAttributes("publication")
@RequestMapping(path = "/template/publish")
public class QuizTemplatePublishController {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private AuthService authService;

    @Autowired
    private QuizAccessManager quizAccessManager;

    @RequestMapping(method = RequestMethod.GET)
    public String show(ModelMap model, @RequestParam int quizId) {
        QuizTemplate quizTemplate = quizDao.getQuizTemplate(quizId);
        quizAccessManager.canEdit(quizTemplate);
        Publication publication = quizDao.getPublicationByQuiz(quizTemplate);

        if (publication == null) {
            publication = new Publication();
            publication.setQuizTemplate(quizTemplate);
        }

        model.put("publication", publication);
        model.put("publishOptions", PublishFor.values());

        return "quizTemplate/publish";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String publish(@ModelAttribute Publication publication) {
        QuizTemplate quizTemplate = publication.getQuizTemplate();
        quizAccessManager.canEdit(quizTemplate);
        publication.setPublishedOn(new Date());

        if (!quizTemplate.isPublished()) {
            quizTemplate.setPublished(true);
            quizDao.save(quizTemplate);
        }
        quizDao.save(publication);

        return "redirect:list";
    }

    @ModelAttribute("contacts")
    public Set<String> getContacts() {
        return quizDao.getUserContacts(authService.getUser());
    }
}
