package com.example.lms.controller;

import com.example.lms.model.Assessment;
import com.example.lms.service.AssessmentService;
import java.util.List;

public class AssessmentController {
    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    public List<Assessment> getAllAssessments() {
        return assessmentService.getAllAssessments();
    }

    public Assessment getAssessmentById(Long id) {
        return assessmentService.getAssessmentById(id);
    }

    public void createAssessment(Assessment assessment) {
        assessmentService.createAssessment(assessment);
    }

    public void deleteAssessment(Long id) {
        assessmentService.deleteAssessment(id);
    }
}
