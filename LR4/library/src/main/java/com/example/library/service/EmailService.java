package com.example.library.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.notification.email}")
    private String notificationEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendChangeNotification(String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notificationEmail);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
