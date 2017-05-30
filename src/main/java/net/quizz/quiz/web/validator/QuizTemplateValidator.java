package net.quizz.quiz.web.validator;

import net.quizz.common.utils.Utils;
import net.quizz.quiz.domain.template.QuestionTemplate;
import net.quizz.quiz.domain.template.QuizTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * @author lutfun
 * @since 5/6/17
 */

@Component
public class QuizTemplateValidator {

    public void validateForCompletion(QuizTemplate quizTemplate, BindingResult errors) {
        for (QuestionTemplate questionTemplate : quizTemplate.getQuestionTemplates()) {
            if (!questionTemplate.hasRightAnswer()) {
                errors.reject("error.quizTemplate.correctAnswerNotSet",
                        new Object[] {questionTemplate.getLabel()},
                        "Correct answer is not set for the Question '{0}'");
            }
        }

        if (Utils.isEmpty(quizTemplate.getQuestionTemplates())) {
            errors.reject("error.quizTemplate.canNotBePublishedWithoutQuestion",
                    "Can not be published without Question");
        }
    }
}
