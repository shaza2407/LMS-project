package com.example.lms.service;

import com.example.lms.model.Notification;
import com.example.lms.repository.CourseRepository;
import com.example.lms.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService
{

    @Autowired
    private NotificationRepository notificationRepository;


    public List<Notification> getAllNotifications()
    {
        return notificationRepository.findAll();
    }

    public Notification addNotification(Notification notification)
    {
        return notificationRepository.save(notification);
    }

    public Notification markNotificationAsRead(long Id)
    {
        if(notificationRepository.existsById(Id))
        {
            Notification notification = notificationRepository.getById(Id);
            notification.markAsRead();
            return notification;
        }
        return null;
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
