package com.example.lms.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int duration; // in weeks

    @ManyToOne
    private User instructor;


    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "course")
    private List<Lesson> lessons;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Question> questionBank; // All questions for this course
}
