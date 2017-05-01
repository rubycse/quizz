package net.quizz.auth.service;

import net.quizz.auth.domain.User;
import net.quizz.auth.repositpry.UserDao;
import net.quizz.common.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.UUID;

/**
 * @author lutfun
 * @since 5/1/17
 */

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailService mailService;

    @Transactional
    public void signUpUser(User user, HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString();
        user.setEmailVerificationId(uuid);
        user.setEmailVerified(false);

        userDao.save(user);
        sendEmail(user, uuid, getURLWithServletPath(request));
    }

    private void sendEmail(User user, String emailVerificationId, String urlWithServletPath) {
        String mailText = "Hi " + user.getFirstName() +
                "\n\nHelp us secure your Quizz account by verifying your email address (" + user.getEmail() + ") by clicking following link:" +
                "\n\n" + urlWithServletPath + "/verify-email/" + emailVerificationId +
                "\n\nThis lets you access all of Quizz's features.\n";
        mailService.sendMail(user.getEmail(), "[Quizz] Please verify your email address", mailText);
    }

    public static String getURLWithServletPath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + request.getServletPath();
    }
}
