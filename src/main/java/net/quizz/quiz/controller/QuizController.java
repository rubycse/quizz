package net.quizz.quiz.controller;

import net.quizz.auth.domain.User;
import net.quizz.common.service.AuthService;
import net.quizz.quiz.domain.Quiz;
import net.quizz.quiz.domain.template.QuizTemplate;
import net.quizz.quiz.repository.QuizDao;
import net.quizz.quiz.service.QuizAccessManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @author lutfun
 * @since 4/22/17
 */

@Controller
@RequestMapping(path = "/quiz", method = RequestMethod.GET)
public class QuizController {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private AuthService authService;

    @Autowired
    private QuizAccessManager quizAccessManager;

    @RequestMapping(path = "/run", method = RequestMethod.GET)
    public String run(@RequestParam int id, ModelMap model) {
        QuizTemplate quizTemplate = quizDao.getQuizTemplate(id);
        User user = authService.getUser();
        quizAccessManager.canAnswer(quizTemplate);
        Quiz quiz = quizDao.getQuizAnswer(quizTemplate, user);

        if (quiz == null) {
            quiz = new Quiz(quizTemplate);
            quiz.setStartTime(new Date());
            quiz.setAnsweredBy(user);
        }

        model.put("quiz", quiz);
        return "quizTemplate/run";
    }

//    @RequestMapping(path = "/run", method = RequestMethod.POST)
//    public String saveAns(@ModelAttribute QuestionTemplate question, ModelMap model) {
//
//        QuizTemplate quiz = quizDao.getQuizTemplate(id);
//        model.put("quiz", quiz);
//        return "quiz/run";
//    }

}
