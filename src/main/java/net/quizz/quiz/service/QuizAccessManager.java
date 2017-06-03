package net.quizz.quiz.service;

import net.quizz.auth.domain.User;
import net.quizz.common.service.AuthService;
import net.quizz.quiz.domain.quiz.Quiz;
import net.quizz.quiz.domain.template.Publication;
import net.quizz.quiz.domain.template.PublishFor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lutfun
 * @since 10/9/16
 */

@Service
public class QuizAccessManager extends AccessManager {

    @Autowired
    private AuthService authService;

    public void canAnswer() {
        throwExceptionIf(!authService.getUser().isStudent());
    }

    public void canView(Quiz quiz) {
        throwExceptionIf(!isCurrentStudent(quiz.getAnsweredBy())
                && !isCurrentTeacher(quiz.getPublication().getPublishedBy()));
    }

    public void canAnswer(Quiz quiz) {
        throwExceptionIf(!isCurrentStudent(quiz.getAnsweredBy()));
    }

    public void canAnswer(Publication publication) {
        User user = authService.getUser();

        switch (publication.getPublishFor()) {
            case SELECTED_USER:
                throwExceptionIf(!publication.getPublishToEmails().contains(user.getEmail()));
                break;
            case GROUP:
                throwExceptionIf(!publication.getStudentGroup().getEmails().contains(user.getEmail()));
                break;
        }
    }
}
