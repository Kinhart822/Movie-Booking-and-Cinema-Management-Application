package com.spring.service;

public interface EmailService {
    void sendSimpleMailMessage(String toEmail, String subject, String body);
    void sendCancelMailMessage(String toEmail);
    void sendDeleteMailMessage(String toEmail);
}
