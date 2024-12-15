package com.example.lms.model;

import java.time.LocalDateTime;

public class Notification {
    private Long id;
    private String recipientRole; // "STUDENT", "INSTRUCTOR", etc.
    private String message;
    private boolean isRead;
    private LocalDateTime timestamp;

    public Notification(Long id, String recipientRole, String message) {
        this.id = id;
        this.recipientRole = recipientRole;
        this.message = message;
        this.isRead = false;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRecipientRole() { return recipientRole; }
    public void setRecipientRole(String recipientRole) { this.recipientRole = recipientRole; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isRead() { return isRead; }
    public void markAsRead() { this.isRead = true; }

    public LocalDateTime getTimestamp() { return timestamp; }
}
