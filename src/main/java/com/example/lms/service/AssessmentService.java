package com.example.lms.service;

import com.example.lms.model.Assessment;
import com.example.lms.repository.AssessmentRepository;
import lombok.AllArgsConstructor;

import java.util.List;
@AllArgsConstructor
public class AssessmentService {
    private final AssessmentRepository assessmentRepository;



    public List<Assessment> getAllAssessments() {
        return assessmentRepository.findAll();
    }

    public Assessment getAssessmentById(Long id) {
        return assessmentRepository.findById(id);
    }

    public void createAssessment(Assessment assessment) {
        assessmentRepository.save(assessment);
    }

    public void deleteAssessment(Long id) {
        assessmentRepository.deleteById(id);
    }
}
