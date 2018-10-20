package net.quizz.auth.controller;

import net.quizz.auth.domain.Gender;
import net.quizz.auth.domain.User;
import net.quizz.auth.repositpry.UserDao;
import net.quizz.auth.service.UserService;
import net.quizz.auth.validator.SignupValidator;
import net.quizz.common.service.MailService;
import net.quizz.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
    private UserService userService;

    @Autowired
    private SignupValidator signupValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        SimpleDateFormat sf = new SimpleDateFormat(DateUtils.DATE_FORMAT);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sf, true));
        binder.addValidators(signupValidator);
    }

    @RequestMapping(path = "/signup", method = RequestMethod.GET)
    public String signup(@ModelAttribute User user) {
        return "signup";
    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute User user, BindingResult result, ModelMap model, UriComponentsBuilder ucb) {
        if (result.hasErrors()) {
            return "signup";
        }

        userService.signUpUser(user, ucb);
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

    @ModelAttribute("genders")
    public List<Gender> getGenders() {
        return Arrays.asList(Gender.values());
    }
}
