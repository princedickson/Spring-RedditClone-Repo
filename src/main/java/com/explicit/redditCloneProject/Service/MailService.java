package com.explicit.redditCloneProject.Service;

import com.explicit.redditCloneProject.Model.NotificationEmail;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;
    @Async
    public void sendMail(NotificationEmail notificationEmail) throws RedditErrorException {

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setText(notificationEmail.getBody());
            messageHelper.setFrom("princedickson03@gmai.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
        };
        try{
            mailSender.send(messagePreparator);
            log.info("Activation email sent");
        }catch (MailException e){
            log.error("fail to sent email", e);
            throw new RedditErrorException("failed to sent email" + notificationEmail.getRecipient() + e.getMessage());
        }

    }
}
