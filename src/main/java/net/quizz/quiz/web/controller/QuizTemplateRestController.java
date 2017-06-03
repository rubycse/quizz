package net.quizz.quiz.web.controller;

import net.quizz.quiz.domain.template.OptionTemplate;
import net.quizz.quiz.domain.template.QuestionTemplate;
import net.quizz.quiz.domain.template.QuizTemplate;
import net.quizz.quiz.repository.QuizTemplateDao;
import net.quizz.quiz.service.QuizTemplateAccessManager;
import net.quizz.quiz.utils.QuizUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lutfun
 * @since 10/3/16
 */

@RestController
@RequestMapping(path = "/template")
public class QuizTemplateRestController {

    @Autowired
    private QuizTemplateDao quizTemplateDao;

    @Autowired
    private QuizTemplateAccessManager quizTemplateAccessManager;

    @RequestMapping(path = "/addQuestion", method = RequestMethod.POST)
    public QuestionTemplate addQuestion(@RequestParam int quizId) {
        QuizTemplate quizTemplate = quizTemplateDao.getQuizTemplate(quizId);
        quizTemplateAccessManager.canEdit(quizTemplate);
        QuestionTemplate questionTemplate = QuizUtils.addQuestion(quizTemplate);
        quizTemplateDao.save(questionTemplate);

        return questionTemplate;
    }

    @RequestMapping(path = "/addOption", method = RequestMethod.POST)
    public OptionTemplate addOption(@RequestParam int questionId) {
        QuestionTemplate questionTemplate = quizTemplateDao.getQuestionTemplate(questionId);
        quizTemplateAccessManager.canEdit(quizTemplateDao.getQuizTemplate(questionTemplate));

        OptionTemplate optionTemplate = QuizUtils.addOption(questionTemplate);
        quizTemplateDao.save(optionTemplate);

        return optionTemplate;
    }

    @RequestMapping(path = "/updateQuizName", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String updateQuizName(@RequestParam int id, @RequestParam String value) {
        QuizTemplate quizTemplate = quizTemplateDao.getQuizTemplate(id);
        quizTemplateAccessManager.canEdit(quizTemplate);

        quizTemplate.setName(value);
        quizTemplateDao.save(quizTemplate);
        return quizTemplate.getName();
    }

    @RequestMapping(path = "/updateQuestionLabel", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String updateQuestionLabel(@RequestParam int id, @RequestParam String value) {
        QuestionTemplate questionTemplate = quizTemplateDao.getQuestionTemplate(id);
        quizTemplateAccessManager.canEdit(quizTemplateDao.getQuizTemplate(questionTemplate));
        questionTemplate.setLabel(value);
        quizTemplateDao.save(questionTemplate);
        return questionTemplate.getLabel();
    }

    @RequestMapping(path = "/updateOptionLabel", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String updateOptionLabel(@RequestParam int id, @RequestParam String value) {
        OptionTemplate optionTemplate = quizTemplateDao.getOption(id);
        quizTemplateAccessManager.canEdit(quizTemplateDao.getQuizTemplate(quizTemplateDao.getQuestionTemplate(optionTemplate)));
        optionTemplate.setLabel(value);
        quizTemplateDao.save(optionTemplate);
        return optionTemplate.getLabel();
    }

    @RequestMapping(path = "/toggleRequired", method = RequestMethod.POST)
    public String toggleRequired(@RequestParam int questionId) {
        QuestionTemplate questionTemplate = quizTemplateDao.getQuestionTemplate(questionId);
        quizTemplateAccessManager.canEdit(quizTemplateDao.getQuizTemplate(questionTemplate));
        questionTemplate.setRequired(!questionTemplate.isRequired());
        quizTemplateDao.save(questionTemplate);
        return "SUCCESS";
    }

    @RequestMapping(path = "/deleteQuestion", method = RequestMethod.POST)
    public String deleteQuestion(@RequestParam int id) {
        QuestionTemplate questionTemplate = quizTemplateDao.getQuestionTemplate(id);
        quizTemplateAccessManager.canEdit(quizTemplateDao.getQuizTemplate(questionTemplate));
        quizTemplateDao.deleteQuestion(id);
        return "SUCCESS";
    }

    @RequestMapping(path = "/deleteOption", method = RequestMethod.POST)
    public String deleteOption(@RequestParam int id) {
        OptionTemplate optionTemplate = quizTemplateDao.getOption(id);
        QuestionTemplate questionTemplate = quizTemplateDao.getQuestionTemplate(optionTemplate);
        quizTemplateAccessManager.canEdit(quizTemplateDao.getQuizTemplate(questionTemplate));
        quizTemplateDao.deleteOption(id);
        return "SUCCESS";
    }

    @RequestMapping(path = "/updateOption", method = RequestMethod.POST)
    public String updateOption(@RequestParam int id) {
        OptionTemplate optionTemplate = quizTemplateDao.getOption(id);
        QuestionTemplate questionTemplate = quizTemplateDao.getQuestionTemplate(optionTemplate);
        quizTemplateAccessManager.canEdit(quizTemplateDao.getQuizTemplate(questionTemplate));
        questionTemplate.clearOption();
        optionTemplate.setRightAnswer(true);
        quizTemplateDao.save(questionTemplate);
        return "SUCCESS";
    }
}
