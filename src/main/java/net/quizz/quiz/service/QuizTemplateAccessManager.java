package net.quizz.quiz.service;

import net.quizz.common.service.AuthService;
import net.quizz.quiz.domain.template.QuizTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lutfun
 * @since 10/9/16
 */

@Service
public class QuizTemplateAccessManager extends AccessManager {

    @Autowired
    private AuthService authService;

    public void canCreate() {
        throwExceptionIf(authService.getUser().isStudent());
    }

    public void canView(QuizTemplate quizTemplate) {
        throwExceptionIf(!isCurrentTeacher(quizTemplate.getCreatedBy()));
    }

    public void canEdit(QuizTemplate quizTemplate) {
        throwExceptionIf(!isCurrentTeacher(quizTemplate.getCreatedBy()) || quizTemplate.isComplete());
    }

    public void canDelete(QuizTemplate quizTemplate) {
        canEdit(quizTemplate);
    }

    public void canComplete(QuizTemplate quizTemplate) {
        canEdit(quizTemplate);
    }

    public void canPublish(QuizTemplate quizTemplate) {
        throwExceptionIf(!isCurrentTeacher(quizTemplate.getCreatedBy()) || !quizTemplate.isComplete());
    }
}
