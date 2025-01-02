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
    public void sendSimpleMailMessage(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(fromEmail);
        javaMailSender.send(message);
    }

    @Override
    public void sendCancelMailMessage(String toEmail) {
        String subject = "Cancel Booking Successfully!";
        String message = """
                Dear Customer,

                We are pleased to inform you that your booking has been successfully canceled.
               
                Thank you for choosing our service!

                Best Regards,
                Your Booking Team""";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
    }

    @Override
    public void sendDeleteMailMessage(String toEmail) {
        String subject = "Delete Booking Successfully";
        String message = """
                Dear Customer,

                We are pleased to inform you that your booking has been successfully deleted.
               
                Thank you for choosing our service!

                Best Regards,
                Your Booking Team""";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
    }

    @Override
    public void sendReinstateMailMessage(String toEmail) {
        String subject = "Booking Reinstated Successfully";
        String message = """
                Dear Customer,

                We are pleased to inform you that your booking has been successfully reinstated.
                You can now proceed to manage your booking or complete the payment if necessary.

                Thank you for choosing our service!

                Best Regards,
                Your Booking Team""";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
    }
}
