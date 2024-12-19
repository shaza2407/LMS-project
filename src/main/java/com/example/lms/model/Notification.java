package com.example.lms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class Notification
{
    private Long id;
    private String recipientRole; // "STUDENT", "INSTRUCTOR", etc.
    private String message;
    private boolean isRead;
    private LocalDateTime timestamp;

    public Notification(Long id, String recipientRole, String message)
    {
        this.id = id;
        this.recipientRole = recipientRole;
        this.message = message;
        this.isRead = false;
        this.timestamp = LocalDateTime.now();
    }


    public boolean isRead()
    {
        return isRead;
    }

    public void markAsRead()
    {
        this.isRead = true;
    }
}
