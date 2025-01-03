package com.spring.service.impl;

import com.spring.dto.response.view.NotificationResponse;
import com.spring.entities.Notification;
import com.spring.repository.NotificationRepository;
import com.spring.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public NotificationResponse getNotificationsByUserId(Integer userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);

        if (notifications.isEmpty()) {
            return new NotificationResponse(Collections.emptyList(), Collections.emptyList());
        }
        notifications.sort((n1, n2) -> n2.getDateCreated().compareTo(n1.getDateCreated()));

        List<String> messages = notifications.stream()
                .map(Notification::getMessage)
                .toList();

        List<String> elapsedTimes = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (Notification notification : notifications) {
            // Calculate elapsed time
            Duration duration = Duration.between(notification.getDateCreated(), now);
            long days = duration.toDays();
            long hours = duration.toHours() % 24;
            long minutes = duration.toMinutes() % 60;

            String elapsedTime;
            if (days > 0) {
                elapsedTime = "%d days ago".formatted(days);
            } else if (hours > 0) {
                elapsedTime = "%d hours ago".formatted(hours);
            } else {
                elapsedTime = "%d minutes ago".formatted(minutes);
            }
            elapsedTimes.add(elapsedTime);
        }

        return new NotificationResponse(messages, elapsedTimes);
    }
}
