package com.example.lms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
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




    public boolean isRead()
    {
        return isRead;
    }

    public void markAsRead()
    {
        this.isRead = true;
    }
}
