package com.example.lms.service;
import com.example.lms.model.Assessment;
import com.example.lms.repository.AssessmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AssessmentService {
    private final AssessmentRepository assessmentRepository;
    public void createQuiz(Long courseId, Assessment quiz) {//take the course id and assessment object
        quiz.setCourseId(courseId);                        //assign this quiz to specific course
        quiz.setType("QUIZ");                               //set the type of the assessment to quiz
        assessmentRepository.save(quiz);                     //add to assessment repository
    }

    public void createAssignment(Long courseId, Assessment assignment) {//take the course id and assessment object
        assignment.setCourseId(courseId);
        assignment.setType("ASSIGNMENT");
        assessmentRepository.save(assignment);
    }

    public void gradeQuiz(Long quizId, String grade) {
        // Logic to grade quiz submissions
        Assessment quiz = assessmentRepository.findById(quizId);
        quiz.setGrade(grade);
        assessmentRepository.save(quiz);
    }

    public String getTrackingData(Long courseId) {
        // Logic to fetch attendance and scores
        return "Attendance and scores for course: " + courseId;
    }

    // Existing Methods
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
