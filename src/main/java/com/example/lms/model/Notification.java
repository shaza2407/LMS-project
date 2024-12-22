package com.example.lms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Notification
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipientRole; // "STUDENT", "INSTRUCTOR", etc.
    private String message;
    private boolean isRead;
    private LocalDateTime timestamp;

    public Notification(String recipientRole, String message, LocalDateTime timestamp)
    {
        this.recipientRole = recipientRole;
        this.message = message;
        this.isRead = false;
        this.timestamp = timestamp;
    }

    public Notification()
    {
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
