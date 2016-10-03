package net.quizz.quiz.controller;

import net.quizz.auth.domain.User;
import net.quizz.common.service.AuthService;
import net.quizz.quiz.domain.Quiz;
import net.quizz.quiz.repository.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author lutfun
 * @since 3/28/16
 */

@Controller
public class QuizController {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private AuthService authService;

    @RequestMapping(path = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute Quiz quiz) {
        return "quiz/create";
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String save(@ModelAttribute Quiz quiz, ModelMap model, HttpSession session) {
        User user = authService.getUser(session);
        quiz.setCreatedBy(user);
        quizDao.save(quiz);
        model.put("id", quiz.getId());
        return "redirect:show";
    }

    @RequestMapping(path = "/show", method = RequestMethod.GET)
    public String show(@RequestParam int id, ModelMap model) {
        Quiz quiz = quizDao.getQuiz(id);
        model.put("quiz", quiz);
        return quiz.isPublished() ? "quiz/show" : "quiz/edit";
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String list(ModelMap model, HttpSession session) {
        User user = authService.getUser(session);
        model.put("quizzes", quizDao.getUserQuizzes(user));
        return "quiz/list";
    }
}
