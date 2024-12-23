package com.example.lms.controller;

import com.example.lms.model.Notification;
import com.example.lms.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

        List<Notification> notifications = notificationService.getAllNotifications();
        for(int i = 0 ; i < notifications.size() ; i++)
        {
            notifications.get(i).markAsRead();
        }
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/unread")
    public ResponseEntity<List<Notification>> getAllUnreadNotifications(String role)
    {
        List<Notification> allNotification = notificationService.getAllNotifications();
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

    // Dummy Example for how to use mail service in service files
//    @Autowired
//    private NotificationService notification;
//    @GetMapping("/Email")
//    public String testEmail()
//    {
//        notification.setNotificationsEmail("modyhassan917@gmail.com", "Lms","This is LMS project bonus;) :)");
//        return "email sent";
//    }
}
