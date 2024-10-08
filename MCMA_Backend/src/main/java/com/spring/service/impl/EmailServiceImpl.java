package com.spring.service.impl;

import com.spring.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendSimpleMailMessage(String toEmail) {
        String subject = "Booking movie ticket(s) successfully";
        String body = "Congratulations! You have successfully booked your movie ticket(s). Thank you!";

        // Create a SimpleMailMessage object
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(fromEmail);
        javaMailSender.send(message);
    }
}
