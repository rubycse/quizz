package net.quizz.common.service;

import net.quizz.auth.domain.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * @author lutfun
 * @since 10/3/16
 */

@Service
public class AuthService {

    public User getUser(HttpSession session) {
        return (User) session.getAttribute("USER");
    }
}
