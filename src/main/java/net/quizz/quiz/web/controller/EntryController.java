package net.quizz.quiz.web.controller;

import net.quizz.auth.domain.User;
import net.quizz.common.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author lutfun
 * @since 5/12/17
 */

@Controller
public class EntryController {

    @Autowired
    private AuthService authService;

    @RequestMapping(path = "/entry", method = RequestMethod.GET)
    public String entry() {
        User user = authService.getUser();
        return "redirect:" + (user.isStudent() ? "/quiz/publication/sharedWithMe" : "/quiz/template/myTemplates");
    }
}
