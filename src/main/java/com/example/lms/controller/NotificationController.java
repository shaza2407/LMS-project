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
    public List<Notification> getAllNotifications(String role)
    {

        List<Notification> notifications = notificationService.getAllNotifications();
        return notifications;
    }

    @PostMapping("/{notificationId}/markAsRead")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable Long notificationId)
    {
        Notification updatedNotification = notificationService.markNotificationAsRead(notificationId);

        if (updatedNotification != null)
        {
            return ResponseEntity.ok().build();  // Return 200 OK if the notification was found and updated
        }
        return ResponseEntity.notFound().build();  // Return 404 if the notification was not found
    }


    @GetMapping("/unread")
    public ResponseEntity<List<Notification>> getAllUnreadNotifications(String role)
    {
        List<Notification> allNotification = notificationService.getAllNotifications();
        List<Notification> unreadNotification = new ArrayList<>();
        for (int i = 0; i < allNotification.size(); i++)
        {
            if (!allNotification.get(i).isRead())
            {
                unreadNotification.add(allNotification.get(i));
            }
        }
        return ResponseEntity.ok(unreadNotification);
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
