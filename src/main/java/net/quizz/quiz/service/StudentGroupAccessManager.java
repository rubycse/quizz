package net.quizz.quiz.service;

import net.quizz.common.service.AuthService;
import net.quizz.quiz.domain.template.StudentGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lutfun
 * @since 6/3/17
 */

@Service
public class StudentGroupAccessManager extends AccessManager {

    @Autowired
    private AuthService authService;

    public void canCreate() {
        throwExceptionIf(authService.getUser().isStudent());
    }

    public void canEdit(StudentGroup studentGroup) {
        throwExceptionIf(!isCurrentTeacher(studentGroup.getCreatedBy()));
    }
}
