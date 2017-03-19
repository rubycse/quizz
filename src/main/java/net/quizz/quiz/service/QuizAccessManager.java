package net.quizz.quiz.service;

import net.quizz.auth.domain.User;
import net.quizz.common.service.AuthService;
import net.quizz.quiz.domain.template.QuizTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lutfun
 * @since 10/9/16
 */

@Service
public class QuizAccessManager {

    @Autowired
    private AuthService authService;

    public void canEdit(QuizTemplate quizTemplate) {
        User user = authService.getUser();
        if (user.getId() != quizTemplate.getCreatedBy().getId()) {
            throw new IllegalAccessError("Illegal Access");
        }
    }

    public void canCreate() {
        User user = authService.getUser();
        if (user.isStudent()) {
            throw new IllegalAccessError("Illegal Access");
        }
    }

    public void canAnswer(QuizTemplate quizTemplate) {
        User user = authService.getUser();
        //TODO: Implement method
    }
}
