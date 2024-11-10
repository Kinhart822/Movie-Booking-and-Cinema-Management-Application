package com.spring.service;

import com.spring.dto.response.view.NotificationResponse;

public interface NotificationService {
    NotificationResponse getNotificationsByUserId(Integer userId);
}
