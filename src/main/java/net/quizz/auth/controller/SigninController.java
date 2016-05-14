package net.quizz.auth.controller;

import net.quizz.auth.domain.Credential;
import net.quizz.auth.domain.User;
import net.quizz.auth.repositpry.UserDao;
import net.quizz.common.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author lutfun
 * @since 5/7/16
 */
@Controller
public class SigninController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(path = "/signin", method = RequestMethod.GET)
    public String showSignin(@ModelAttribute Credential credential) {
        return "signin";
    }

    @RequestMapping(path = "/signin", method = RequestMethod.POST)
    public String signin(@ModelAttribute Credential credential, BindingResult result, ModelMap model, HttpServletRequest request) {
        User user = userDao.getAuthenticUser(credential);
        if (user == null) {
            result.reject("", "Invalid Username/Password");
            return "signin";
        } else if (!user.isEmailVerified()) {
            model.put("email", user.getEmail());
            return "verifyEmail";
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("USER", user);

        return "redirect:/quiz/list";
    }

    @RequestMapping(path = "/signout", method = RequestMethod.GET)
    public String signout(HttpSession session) {
        session.invalidate();
        return "redirect:signin";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/DD/YYYY"), false));
    }
}
