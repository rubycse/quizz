package net.quizz.quiz.controller;

import net.quizz.common.service.AuthService;
import net.quizz.quiz.domain.Publication;
import net.quizz.quiz.domain.PublishFor;
import net.quizz.quiz.domain.Quiz;
import net.quizz.quiz.repository.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Set;

/**
 * @author lutfun
 * @since 10/4/16
 */

@Controller
@SessionAttributes("publication")
public class QuizPublishController {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private AuthService authService;

    @RequestMapping(path = "/publish", method = RequestMethod.GET)
    public String show(ModelMap model, @RequestParam int quizId) {
        Quiz quiz = quizDao.getQuiz(quizId);
        Publication publication = quizDao.getPublicationByQuiz(quiz);

        if (publication == null) {
            publication = new Publication();
            publication.setQuiz(quiz);
        }

        model.put("publication", publication);
        model.put("publishOptions", PublishFor.values());

        return "quiz/publish";
    }

    @RequestMapping(path = "/publish", method = RequestMethod.POST)
    public String publish(@ModelAttribute Publication publication) {
        publication.setPublishedOn(new Date());
        Quiz quiz = publication.getQuiz();

        if (!quiz.isPublished()) {
            quiz.setPublished(true);
            quizDao.save(quiz);
        }
        quizDao.save(publication);

        return "redirect:list";
    }

    @ModelAttribute("contacts")
    public Set<String> getContacts(HttpSession session) {
        return quizDao.getUserContacts(authService.getUser(session));
    }
}
