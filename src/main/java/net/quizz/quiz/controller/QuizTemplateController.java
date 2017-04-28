package net.quizz.quiz.controller;

import net.quizz.auth.domain.User;
import net.quizz.common.service.AuthService;
import net.quizz.quiz.domain.template.Publication;
import net.quizz.quiz.domain.template.QuizTemplate;
import net.quizz.quiz.repository.QuizDao;
import net.quizz.quiz.service.QuizAccessManager;
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
@RequestMapping(path = "/template")
public class QuizTemplateController {

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
    }

    @RequestMapping(path = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute QuizTemplate quizTemplate) {
        quizAccessManager.canCreate();
        return "quizTemplate/create";
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute QuizTemplate quizTemplate, BindingResult result, ModelMap model) {
        quizAccessManager.canCreate();
        if (result.hasErrors()) {
            return "quizTemplate/create";
        }
        User user = authService.getUser();
        quizTemplate.setCreatedBy(user);
        quizDao.save(quizTemplate);
        model.put("id", quizTemplate.getId());
        return "redirect:show";
    }

    @RequestMapping(path = "/show", method = RequestMethod.GET)
    public String show(@RequestParam int id, ModelMap model) {
        QuizTemplate quizTemplate = quizDao.getQuizTemplate(id);
        User user = authService.getUser();
        model.put("quizTemplate", quizTemplate);
        boolean createdByUser = quizTemplate.getCreatedBy().getId() == user.getId();
        return createdByUser ? (quizTemplate.isPublished() ? "quizTemplate/show" : "quizTemplate/edit") : "quizTemplate/showPublic";
    }

    @RequestMapping(path = "/myTemplates", method = RequestMethod.GET)
    public String list(ModelMap model) {
        quizAccessManager.canCreate();
        User user = authService.getUser();
        model.put("quizzes", quizDao.getUserQuizTemplates(user));
        model.put("myQuizzes", true);
        return "quizTemplate/myTemplates";
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
        return "quizTemplate/list";
    }
}
