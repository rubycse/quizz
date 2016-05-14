package net.quizz.auth.controller;

import net.quizz.auth.domain.Gender;
import net.quizz.auth.domain.User;
import net.quizz.auth.repositpry.UserDao;
import net.quizz.common.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author lutfun
 * @since 5/13/16
 */
@Controller
public class SignupController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailService mailService;

    @RequestMapping(path = "/signup", method = RequestMethod.GET)
    public String signup(@ModelAttribute User user) {
        return "signup";
    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute User user, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "signup";
        }

        String uuid = UUID.randomUUID().toString();
        user.setEmailVerificationId(uuid);
        user.setEmailVerified(false);
        userDao.save(user);
        sendEmail(user, uuid);
        model.put("email", user.getEmail());

        return "verifyEmail";
    }

    @RequestMapping(path = "/verify-email/{verificationId}")
    public String verifyEmail(@PathVariable String verificationId, ModelMap model) {
        boolean verificationSuccessful = userDao.verifyUserEmail(verificationId);
        String message = verificationSuccessful ? "Your email is verified successfully"
                : "Something went wrong";
        model.put("message", message);
        return "emailVerified";
    }

    private void sendEmail(User user, String emailVerificationId) {
        String mailText = "Hi " + user.getFirstName() +
                "\n\nHelp us secure your Quizz account by verifying your email address (" + user.getEmail() + ") by clicking following link:" +
                "\n\nhttp://localhost:8090/quizz/auth/verify-email/" + emailVerificationId +
                "\n\nThis lets you access all of Quizz's features.\n";
        mailService.sendMail(user.getEmail(), "[Quizz] Please verify your email address", mailText);
    }

    @ModelAttribute("genders")
    public List<Gender> getGenders() {
        return Arrays.asList(Gender.values());
    }
}
