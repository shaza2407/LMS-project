package com.example.lms.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter
@Entity
public class Lesson {
     @Id //change it to the annotation of jakarta
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String mediaPath; // URL or file path for videos, PDFs, etc.

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String otp; // OTP for attendance validation

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "lesson")
    private List<Attendance> attendanceRecords;
}
