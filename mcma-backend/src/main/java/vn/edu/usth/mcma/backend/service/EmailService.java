package vn.edu.usth.mcma.backend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    public void sendSimpleMailMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    public void send() {

    }

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
