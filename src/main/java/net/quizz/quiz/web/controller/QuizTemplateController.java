package net.quizz.quiz.web.controller;

import net.quizz.auth.domain.User;
import net.quizz.common.service.AuthService;
import net.quizz.common.utils.Utils;
import net.quizz.quiz.domain.template.QuizTemplate;
import net.quizz.quiz.repository.QuizTemplateDao;
import net.quizz.quiz.service.QuizTemplateAccessManager;
import net.quizz.quiz.web.validator.QuizTemplateValidator;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import java.io.IOException;

import static net.quizz.quiz.utils.QuizUtils.createQuizTemplate;

/**
 * @author lutfun
 * @since 3/28/16
 */

@Controller
@RequestMapping(path = "/template")
public class QuizTemplateController {

    @Autowired
    private QuizTemplateDao quizTemplateDao;

    @Autowired
    private AuthService authService;

    @Autowired
    private QuizTemplateAccessManager quizTemplateAccessManager;

    @Autowired
    private QuizTemplateValidator quizTemplateValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String save(ModelMap model) {
        quizTemplateAccessManager.canCreate();
        User user = authService.getUser();
        QuizTemplate quizTemplate = createQuizTemplate();
        quizTemplate.setCreatedBy(user);
        quizTemplateDao.save(quizTemplate);
        model.put("id", quizTemplate.getId());

        return "redirect:show";
    }

    @RequestMapping(path = "/myTemplates", method = RequestMethod.GET)
    public String list(ModelMap model) {

        quizTemplateAccessManager.canCreate();
        User user = authService.getUser();

        model.put("quizzes", quizTemplateDao.getUserQuizTemplates(user));
        model.put("myTemplates", true);

        return "quizTemplate/myTemplates";
    }

    @RequestMapping(path = "/show", method = RequestMethod.GET)
    public String show(@RequestParam int id, ModelMap model,
                       @RequestParam(defaultValue = "false") boolean pdf,
                       HttpServletResponse response) throws DocumentException, TransformerException, IOException, ParserConfigurationException, SAXException {

        QuizTemplate quizTemplate = quizTemplateDao.getQuizTemplate(id);
        quizTemplateAccessManager.canView(quizTemplate);

        model.put("quizTemplate", quizTemplate);

        if (pdf) {
            Utils.writeToResponse(quizTemplate, quizTemplate.getClass(), "quizTemplate", response);
            return null;
        }

        System.out.println(Utils.getXml(quizTemplate, QuizTemplate.class));

        return quizTemplate.isComplete() ? "quizTemplate/show" : "quizTemplate/edit";
    }

    @RequestMapping(path = "/show", method = RequestMethod.POST, params = "_complete")
    public String complete(@ModelAttribute("quizTemplate") QuizTemplate quizTemplate, BindingResult errors,
                           ModelMap model, RedirectAttributes redirectAttributes) {

        quizTemplate = quizTemplateDao.getQuizTemplate(quizTemplate.getId());

        quizTemplateAccessManager.canComplete(quizTemplate);

        quizTemplateValidator.validateForCompletion(quizTemplate, errors);

        if (errors.hasErrors()) {
            // As quizTemplate is not stored in session it need to be retrieved from DB and put
            // in the model to render jsp. As ModelAttribute is changed, the associated BindingResult
            // is lost too. So, the BindingResult is also put in the model, to render the error.
            model.put("quizTemplate", quizTemplate);
            model.put("errors", errors);

            return "quizTemplate/edit";
        }

        quizTemplate.setComplete(true);
        quizTemplateDao.save(quizTemplate);

        redirectAttributes.addAttribute("id", quizTemplate.getId());

        return "redirect:show";
    }

    @RequestMapping(path = "/show", method = RequestMethod.POST, params = "delete")
    public String delete(@RequestParam int id) {

        QuizTemplate quizTemplate = quizTemplateDao.getQuizTemplate(id);
        quizTemplateAccessManager.canDelete(quizTemplate);
        quizTemplateDao.delete(quizTemplate);

        return "redirect:/quiz/template/myTemplates";
    }
}
