package net.quizz.auth.validator;

import net.quizz.auth.domain.User;
import net.quizz.auth.repositpry.UserDao;
import net.quizz.common.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author lutfun
 * @since 10/18/16
 */
@Component
public class SignupValidator implements Validator {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (!Utils.isEmpty(user.getPassword()) && !Utils.isEmpty(user.getConfirmPassword()) &&
                !user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "error.confirmPassword.doesNotMatch");
        }

        if (!Utils.isEmpty(user.getUsername()) && userDao.isUsernameExists(user.getUsername())) {
            errors.rejectValue("username", "auth.username.alreadyTaken");
        }

        if (!Utils.isEmpty(user.getEmail()) && userDao.isEmailExists(user.getEmail())) {
            errors.rejectValue("email", "auth.email.alreadyUsed");
        }
    }
}
