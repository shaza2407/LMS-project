package com.example.lms.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String mediaPath; // URL or file path for videos, PDFs, etc.

    @ManyToOne
    private Course course;

    @OneToMany(mappedBy = "lesson")
    private List<Attendance> attendanceRecords;
}
