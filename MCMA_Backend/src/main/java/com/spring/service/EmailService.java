package com.spring.service;

public interface EmailService {
    void sendSimpleMailMessage(String toEmail);
    void sendDeleteMailMessage(String toEmail);
}
