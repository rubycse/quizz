package net.quizz.quiz.web.controller;

import net.quizz.auth.domain.User;
import net.quizz.common.service.AuthService;
import net.quizz.quiz.domain.template.StudentGroup;
import net.quizz.quiz.repository.StudentGroupDao;
import net.quizz.quiz.service.StudentGroupAccessManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author lutfun
 * @since 5/31/17
 */

@Controller
@SessionAttributes("studentGroup")
@RequestMapping(path = "/group")
public class StudentGroupController {

    @Autowired
    private StudentGroupDao studentGroupDao;

    @Autowired
    private AuthService authService;

    @Autowired
    private StudentGroupAccessManager studentGroupAccessManager;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(path = "/show", method = RequestMethod.GET)
    public String show(ModelMap model, @RequestParam(defaultValue = "0") int id) {

        StudentGroup studentGroup;
        if (id == 0) {
            studentGroup = new StudentGroup();
            studentGroup.setCreatedBy(authService.getUser());
        } else {
            studentGroup = studentGroupDao.getStudentGroup(id);
        }

        studentGroupAccessManager.canEdit(studentGroup);

        model.put("studentGroup", studentGroup);

        return "studentGroup/show";
    }

    @RequestMapping(path = "/show", method = RequestMethod.POST)
    public String save(ModelMap model, @Valid @ModelAttribute StudentGroup studentGroup, BindingResult result) {

        studentGroupAccessManager.canEdit(studentGroup);

        if (result.hasErrors()) {
            return "studentGroup/show";
        }

        studentGroupDao.save(studentGroup);

        model.put("studentGroup", studentGroup);

        return "studentGroup/show";
    }

    @RequestMapping(path = "/show", method = RequestMethod.POST, params = "delete")
    public String delete(ModelMap model, @Valid @ModelAttribute StudentGroup studentGroup, BindingResult errors) {

        studentGroupAccessManager.canEdit(studentGroup);

        try {
            studentGroupDao.delete(studentGroup);
        } catch (DataIntegrityViolationException ex) {
            errors.reject("student.group.cannotBeDeleted");
            return "studentGroup/show";
        }

        return "redirect:list";
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String list(ModelMap model) {

        studentGroupAccessManager.canCreate();

        User user = authService.getUser();

        model.put("studentGroups", studentGroupDao.getStudentGroups(user));

        return "studentGroup/list";
    }
}
