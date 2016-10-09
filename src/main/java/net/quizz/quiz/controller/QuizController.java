package net.quizz.quiz.controller;

import net.quizz.auth.domain.User;
import net.quizz.common.service.AuthService;
import net.quizz.quiz.domain.Publication;
import net.quizz.quiz.domain.Quiz;
import net.quizz.quiz.repository.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Integer.class, null, new CustomNumberEditor(Integer.class, true));
    }

    @RequestMapping(path = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute Quiz quiz) {
        return "quiz/create";
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute Quiz quiz, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "quiz/create";
        }
        User user = authService.getUser();
        quiz.setCreatedBy(user);
        quizDao.save(quiz);
        model.put("id", quiz.getId());
        return "redirect:show";
    }

    @RequestMapping(path = "/show", method = RequestMethod.GET)
    public String show(@RequestParam int id, ModelMap model) {
        Quiz quiz = quizDao.getQuiz(id);
        User user = authService.getUser();
        model.put("quiz", quiz);
        boolean createdByUser = quiz.getCreatedBy().getId() == user.getId();
        return createdByUser ? (quiz.isPublished() ? "quiz/show" : "quiz/edit") : "quiz/showPublic";
    }

    @RequestMapping(path = "/myQuizzes", method = RequestMethod.GET)
    public String list(ModelMap model) {
        User user = authService.getUser();
        model.put("quizzes", quizDao.getUserQuizzes(user));
        model.put("myQuizzes", true);
        return "quiz/myQuizzes";
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String sharedW(ModelMap model, @RequestParam(defaultValue = "false") boolean sharedWithMe) {
        User user = authService.getUser();
        List<Publication> publications = sharedWithMe ? quizDao.getPublicationsSharedWithMe(user)
                : quizDao.getAllPublicPublications();
        model.put("publications", publications);
        if (sharedWithMe) {
            model.put("sharedWithMe", true);
        } else {
            model.put("public", true);
        }
        return "quiz/list";
    }
}
