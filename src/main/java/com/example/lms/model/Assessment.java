package com.example.lms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnore
    private Course course;
    private String title;

    @Enumerated(EnumType.STRING)
    private AssessmentType type; // QUIZ or ASSIGNMENT

    private LocalDateTime deadline;

    @Column(length = 2000)
    private String description;

    private boolean graded; // For tracking if the assessment has been graded
}
