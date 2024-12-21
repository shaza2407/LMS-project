package com.example.lms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Assessment {
    @Id
    private Long id;
    private Long courseId;
    private String content; // Quiz questions or assignment details
    private String grade;
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private String title;

    @Enumerated(EnumType.STRING)
    private AssessmentType type; // QUIZ or ASSIGNMENT

    private LocalDateTime deadline;

    @Column(length = 2000)
    private String description;

    private boolean graded; // For tracking if the assessment has been graded
}
