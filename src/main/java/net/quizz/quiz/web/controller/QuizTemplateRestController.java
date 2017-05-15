package net.quizz.quiz.web.controller;

import net.quizz.quiz.domain.template.OptionTemplate;
import net.quizz.quiz.domain.template.QuestionTemplate;
import net.quizz.quiz.domain.template.QuizTemplate;
import net.quizz.quiz.repository.QuizDao;
import net.quizz.quiz.service.QuizAccessManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @author lutfun
 * @since 10/3/16
 */

@RestController
@RequestMapping(path = "/template")
public class QuizTemplateRestController {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuizAccessManager quizAccessManager;

    @RequestMapping(path = "/addQuestion", method = RequestMethod.POST)
    public QuestionTemplate addQuestion(@RequestParam int quizId) {
        QuizTemplate quizTemplate = quizDao.getQuizTemplate(quizId);
        quizAccessManager.canEdit(quizTemplate);
        QuestionTemplate questionTemplate = addQuestion(quizTemplate);
        quizDao.save(questionTemplate);

        return questionTemplate;
    }

    @RequestMapping(path = "/addOption", method = RequestMethod.POST)
    public OptionTemplate addOption(@RequestParam int questionId) {
        QuestionTemplate questionTemplate = quizDao.getQuestionTemplate(questionId);
        quizAccessManager.canEdit(quizDao.getQuizTemplate(questionTemplate));

        OptionTemplate optionTemplate = addOption(questionTemplate);
        quizDao.save(optionTemplate);

        return optionTemplate;
    }

    @RequestMapping(path = "/updateQuizName", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String updateQuizName(@RequestParam int id, @RequestParam String value) {
        QuizTemplate quizTemplate = quizDao.getQuizTemplate(id);
        quizAccessManager.canEdit(quizTemplate);

        quizTemplate.setName(value);
        quizDao.save(quizTemplate);
        return quizTemplate.getName();
    }

    @RequestMapping(path = "/updateQuestionLabel", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String updateQuestionLabel(@RequestParam int id, @RequestParam String value) {
        QuestionTemplate questionTemplate = quizDao.getQuestionTemplate(id);
        quizAccessManager.canEdit(quizDao.getQuizTemplate(questionTemplate));
        questionTemplate.setLabel(value);
        quizDao.save(questionTemplate);
        return questionTemplate.getLabel();
    }

    @RequestMapping(path = "/updateOptionLabel", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String updateOptionLabel(@RequestParam int id, @RequestParam String value) {
        OptionTemplate optionTemplate = quizDao.getOption(id);
        quizAccessManager.canEdit(quizDao.getQuizTemplate(quizDao.getQuestionTemplate(optionTemplate)));
        optionTemplate.setLabel(value);
        quizDao.save(optionTemplate);
        return optionTemplate.getLabel();
    }

    @RequestMapping(path = "/toggleRequired", method = RequestMethod.POST)
    public String toggleRequired(@RequestParam int questionId) {
        QuestionTemplate questionTemplate = quizDao.getQuestionTemplate(questionId);
        quizAccessManager.canEdit(quizDao.getQuizTemplate(questionTemplate));
        questionTemplate.setRequired(!questionTemplate.isRequired());
        quizDao.save(questionTemplate);
        return "SUCCESS";
    }

    @RequestMapping(path = "/deleteQuestion", method = RequestMethod.POST)
    public String deleteQuestion(@RequestParam int id) {
        QuestionTemplate questionTemplate = quizDao.getQuestionTemplate(id);
        quizAccessManager.canEdit(quizDao.getQuizTemplate(questionTemplate));
        quizDao.deleteQuestion(id);
        return "SUCCESS";
    }

    @RequestMapping(path = "/deleteOption", method = RequestMethod.POST)
    public String deleteOption(@RequestParam int id) {
        OptionTemplate optionTemplate = quizDao.getOption(id);
        QuestionTemplate questionTemplate = quizDao.getQuestionTemplate(optionTemplate);
        quizAccessManager.canEdit(quizDao.getQuizTemplate(questionTemplate));
        quizDao.deleteOption(id);
        return "SUCCESS";
    }

    @RequestMapping(path = "/updateOption", method = RequestMethod.POST)
    public String updateOption(@RequestParam int id) {
        OptionTemplate optionTemplate = quizDao.getOption(id);
        QuestionTemplate questionTemplate = quizDao.getQuestionTemplate(optionTemplate);
        quizAccessManager.canEdit(quizDao.getQuizTemplate(questionTemplate));
        questionTemplate.clearOption();
        optionTemplate.setRightAnswer(true);
        quizDao.save(questionTemplate);
        return "SUCCESS";
    }

    private QuestionTemplate addQuestion(QuizTemplate quizTemplate) {
        int questionSize = quizTemplate.getQuestionTemplates().size();
        QuestionTemplate questionTemplate = new QuestionTemplate("Question " + (questionSize + 1));
        questionTemplate.setOptions(new ArrayList<OptionTemplate>());
        addOption(questionTemplate);
        addOption(questionTemplate);
        quizTemplate.getQuestionTemplates().add(questionTemplate);
        return questionTemplate;
    }

    private OptionTemplate addOption(QuestionTemplate questionTemplate) {
        int optionSize = questionTemplate.getOptions().size();
        OptionTemplate optionTemplate = new OptionTemplate("Option " + (optionSize + 1));
        questionTemplate.getOptions().add(optionTemplate);
        return optionTemplate;
    }
}
