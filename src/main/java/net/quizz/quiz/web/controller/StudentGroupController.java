package net.quizz.quiz.web.controller;

import net.quizz.auth.domain.User;
import net.quizz.auth.repositpry.UserDao;
import net.quizz.common.service.AuthService;
import net.quizz.quiz.domain.template.StudentGroup;
import net.quizz.quiz.repository.QuizDao;
import net.quizz.quiz.service.QuizAccessManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * @author lutfun
 * @since 5/31/17
 */

@Controller
@SessionAttributes("studentGroup")
@RequestMapping(path = "/group")
public class StudentGroupController {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private AuthService authService;

    @Autowired
    private QuizAccessManager quizAccessManager;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(path = "/show", method = RequestMethod.GET)
    public String show(ModelMap model, @RequestParam(defaultValue = "0") int id) {

        StudentGroup studentGroup = id == 0 ? new StudentGroup() : quizDao.getStudentGroup(id);

        model.put("studentGroup", studentGroup);

        return "studentGroup/show";
    }

    @RequestMapping(path = "/show", method = RequestMethod.POST)
    public String save(ModelMap model, @ModelAttribute StudentGroup studentGroup) {

        if (studentGroup.getId() == 0) {
            studentGroup.setCreatedBy(authService.getUser());
        }

        quizDao.save(studentGroup);

        model.put("studentGroup", studentGroup);

        return "studentGroup/show";
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String list(ModelMap model) {

        User user = authService.getUser();

        model.put("studentGroups", quizDao.getStudentGroups(user));

        return "studentGroup/list";
    }
}
