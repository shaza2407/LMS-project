package com.example.lms.service;

import com.example.lms.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService
{
    private final List<Notification> notifications = new ArrayList<>();

    public List<Notification> getAllNotifications(String role)
    {//this should be an id i guess not role based
        List<Notification> filteredNotifications = new ArrayList<>();
        for (Notification notification : notifications)
        {
            if (notification.getRecipientRole().equals(role))
            {
                filteredNotifications.add(notification);
            }
        }
        return filteredNotifications;
    }

    public void addNotification(Notification notification)
    {
        notifications.add(notification);
    }

    public void markNotificationAsRead(Long id)
    {
        notifications.stream()
                .filter(notification -> notification.getId().equals(id))
                .forEach(Notification::markAsRead);
    }

    @Autowired
    private JavaMailSender mailSender;
    public void setNotificationsEmail(String toEmail, String subject, String body)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
    }
}
