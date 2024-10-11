package com.spring.service;

import com.spring.dto.Response.view.NotificationResponse;

public interface NotificationService {
    NotificationResponse getNotificationsByUserId(Integer userId);
}
