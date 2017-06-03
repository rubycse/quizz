package net.quizz.quiz.utils;

import net.quizz.quiz.domain.template.OptionTemplate;
import net.quizz.quiz.domain.template.QuestionTemplate;
import net.quizz.quiz.domain.template.QuizTemplate;

import java.util.ArrayList;

/**
 * @author lutfun
 * @since 6/3/17
 */

public class QuizUtils {

    public static QuizTemplate createQuizTemplate() {
        QuizTemplate quizTemplate = new QuizTemplate();
        quizTemplate.setName("Quiz Unnamed");
        QuizUtils.addQuestion(quizTemplate);
        return quizTemplate;
    }

    public static QuestionTemplate addQuestion(QuizTemplate quizTemplate) {
        int questionSize = quizTemplate.getQuestionTemplates().size();
        QuestionTemplate questionTemplate = new QuestionTemplate("Question " + (questionSize + 1));
        questionTemplate.setOptions(new ArrayList<OptionTemplate>());
        addOption(questionTemplate);
        addOption(questionTemplate);
        quizTemplate.getQuestionTemplates().add(questionTemplate);
        return questionTemplate;
    }

    public static OptionTemplate addOption(QuestionTemplate questionTemplate) {
        int optionSize = questionTemplate.getOptions().size();
        OptionTemplate optionTemplate = new OptionTemplate("Option " + (optionSize + 1));
        questionTemplate.getOptions().add(optionTemplate);
        return optionTemplate;
    }
}
