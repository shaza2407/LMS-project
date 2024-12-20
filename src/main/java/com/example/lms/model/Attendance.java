package com.example.lms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Lesson lesson;

    @ManyToOne
    private User student;

    private String otp; // OTP for lesson attendance
    private LocalDateTime timestamp;

    public Attendance() {

    }

    public Attendance(Long studentId, Long lessonId) {
    }
}
