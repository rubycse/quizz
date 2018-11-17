package net.quizz.common.service;

import net.quizz.common.utils.PropertyReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * @author lutfun
 * @since 5/8/16
 */
@Service
public class MailService {

    @Autowired
    private MailSender mailSender;

    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(PropertyReader.getProperty("mail.from"));
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
