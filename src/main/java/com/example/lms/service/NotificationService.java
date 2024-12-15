package com.example.lms.service;

import com.example.lms.model.Notification;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private final List<Notification> notifications = new ArrayList<>();

    public List<Notification> getAllNotifications(String role) {
        List<Notification> filteredNotifications = new ArrayList<>();
        for (Notification notification : notifications) {
            if (notification.getRecipientRole().equals(role)) {
                filteredNotifications.add(notification);
            }
        }
        return filteredNotifications;
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public void markNotificationAsRead(Long id) {
        notifications.stream()
                .filter(notification -> notification.getId().equals(id))
                .forEach(Notification::markAsRead);
    }
}
