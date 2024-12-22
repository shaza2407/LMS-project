package com.example.lms.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String content; // The question text

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @ElementCollection
    private List<String> options; // For MCQ

    private String correctAnswer; // For validation
}
