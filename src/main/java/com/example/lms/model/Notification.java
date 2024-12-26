package com.example.lms.model;

import jakarta.persistence.*;
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
//    private String recipientRole; // "STUDENT", "INSTRUCTOR", etc.
    private String message;
    private boolean isRead;
    private LocalDateTime timestamp;
    @ManyToOne(fetch = FetchType.EAGER)  // Changed to EAGER
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    public Notification(String message, LocalDateTime timestamp, User sender)
    {
//        this.recipientRole = recipientRole;
        this.message = message;
        this.isRead = false;
        this.timestamp = timestamp;
        this.sender = sender;
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
