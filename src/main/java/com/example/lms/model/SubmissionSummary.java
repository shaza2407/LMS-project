package com.example.lms.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubmissionSummary{
    private Long id;
    private Long studentId;
    private LocalDateTime submittedOn;
    private Double grade;



    // Constructor
    public SubmissionSummary(Long id, Long studentId, LocalDateTime submittedOn , Double grade) {
        this.id = id;
        this.studentId = studentId;
        this.submittedOn = submittedOn;
        this.grade = grade;

    }




}
