package com.gohaibo.gohaibo.serviceimp;

import com.gohaibo.gohaibo.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImp implements EmailService {

    private final JavaMailSender javaMailSender;

    public EmailServiceImp(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmailWithToken(String userEmail, String link) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,"utf-8");

        String subject = "Project Team Invitation";
        String text = "click the link to join the team"+link;

        try {
            helper.setSubject(subject);
            helper.setText(text, true);
            helper.setTo(userEmail);
            javaMailSender.send(message);
        }catch (Exception e){
            throw new MailSendException("Failed to send email");

        }

    }
}
