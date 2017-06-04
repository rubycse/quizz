package net.quizz.quiz.service;

import net.quizz.auth.domain.User;
import net.quizz.common.exception.InsufficientPrivilegeException;
import net.quizz.common.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lutfun
 * @since 6/3/17
 */

@Service
public class AccessManager {

    @Autowired
    private AuthService authService;

    protected boolean isCurrentTeacher(User createdBy) {
        User user = authService.getUser();
        return !user.isStudent() && createdBy.getId() == user.getId();
    }

    protected boolean isCurrentStudent(User createdBy) {
        User user = authService.getUser();
        return user.isStudent() && createdBy.getId() == user.getId();
    }

    protected void throwExceptionIf(boolean condition) {
        if (condition) {
            throw new InsufficientPrivilegeException();
        }
    }
}
