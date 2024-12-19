package com.example.lms.controller;

import com.example.lms.model.Notification;
import com.example.lms.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController
{
    private final NotificationService notificationService;
    // GET all users
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications(String role)
    {
        List<Notification> notification = notificationService.getAllNotifications(role);
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/{unread}")
    public ResponseEntity<List<Notification>> getAllUnreadNotifications(String role)
    {
        List<Notification> allNotification = notificationService.getAllNotifications(role);
        List<Notification> UnreadNotification = new ArrayList<>();
        for (Notification n : allNotification)
        {
            if(!n.isRead())
            {
                UnreadNotification.add(n);
            }
        }
        return ResponseEntity.ok(UnreadNotification);
    }
}
