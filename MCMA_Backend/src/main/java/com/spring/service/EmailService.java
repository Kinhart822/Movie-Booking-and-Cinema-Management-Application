package com.spring.service;

public interface EmailService {
    void sendSimpleMailMessage(String toEmail, String subject, String body);
    void sendDeleteMailMessage(String toEmail);
}
