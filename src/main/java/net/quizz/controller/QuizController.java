package net.quizz.controller;

import net.quizz.domain.Quiz;
import net.quizz.repository.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @author lutfun
 * @since 3/28/16
 */

@RestController
public class QuizController {

    @Autowired
    private QuizDao quizDao;

    @RequestMapping(path = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute Quiz quiz) {
        return "quiz/create";
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String save(@ModelAttribute Quiz quiz, ModelMap model) {
        quizDao.save(quiz);
        model.put("id", quiz.getId());
        return "redirect:show";
    }

    @RequestMapping(path = "/show", method = RequestMethod.GET)
    public String show(@RequestParam int id, ModelMap model) {
        model.put("quiz", quizDao.getQuiz(id));
        return "quiz/show";
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String list(ModelMap model) {
        model.put("quizzes", quizDao.getAllQuizzes());
        return "quiz/list";
    }
}
