package net.quizz.common.service;

import net.quizz.auth.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author lutfun
 * @since 10/3/16
 */

@Service
public class AuthService {

    public User getUser() {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getSession();
        return (User) session.getAttribute("USER");
    }
}
