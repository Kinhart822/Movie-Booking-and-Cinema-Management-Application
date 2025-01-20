package vn.edu.usth.mcma.backend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.domain.Otp;
import vn.edu.usth.mcma.backend.entity.User;

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

    public void sendEmailVerificationOtp(Otp otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(otp.getEmail());
        message.setSubject("MBCM: Verify Your Email");
        String date = otp.getDueDate().toString().substring(0, 10);
        String time = otp.getDueDate().toString().substring(11, 16);
        message.setText(String.format("""
                Lmao!
                OTP: %s
                Please verify your email before %s %s
                """, otp.getOtp(), date, time));
        javaMailSender.send(message);
    }

    public void sendResetPasswordMail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("MBCM: Reset Password");
        message.setText(String.format("""
                Lmao!
                OTP: %s
                """, user.getResetKey()));
        javaMailSender.send(message);
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
