package net.quizz.quiz.service;

import net.quizz.auth.domain.User;
import net.quizz.common.service.AuthService;
import net.quizz.quiz.domain.Quiz;
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

    public void canEdit(Quiz quiz) {
        User user = authService.getUser();
        if (user.getId() != quiz.getCreatedBy().getId()) {
            throw new IllegalAccessError("Illegal Access");
        }
    }

    public void canCreate() {
        User user = authService.getUser();
        if (user.isStudent()) {
            throw new IllegalAccessError("Illegal Access");
        }
    }
}
