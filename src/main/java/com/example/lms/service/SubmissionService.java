package com.example.lms.service;

import com.example.lms.model.Assessment;
import com.example.lms.model.AssessmentType;
import com.example.lms.model.Submission;
import com.example.lms.model.User;
import com.example.lms.repository.AssessmentRepository;
import com.example.lms.repository.SubmissionRepository;
import com.example.lms.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final AssessmentRepository assessmentRepository;
    private final UserRepository userRepository;

    // View submissions for an assessment(by instructor)
    public List<Submission> getSubmissionsForAssessment(Long assessmentId) {
        return submissionRepository.findByAssessmentId(assessmentId);
    }

    // Grade a submission
    public Submission gradeSubmission(Long submissionId, Double grade, String feedback) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
        submission.setGrade(grade);
        submission.setFeedback(feedback);
        return submissionRepository.save(submission);
    }

// By student
    public String submitAssignment(Long studentId, Long assessmentId, String filePath) {
        // Fetch the assessment
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));
        // Ensure it's an assignment type
        if (assessment.getType() != AssessmentType.ASSIGNMENT) {
            throw new RuntimeException("This assessment is not an assignment");
        }

        // Fetch the student entity
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Create and save the submission
        Submission submission = new Submission();
        submission.setStudent(student);
        submission.setAssessment(assessment);
        submission.setFilePath(filePath);
        submission.setSubmittedOn(LocalDateTime.now());

        submissionRepository.save(submission);
        return "Assignment submitted successfully!";
    }

    public List<Submission> getSubmissionsByStudent(Long studentId) {
        return submissionRepository.findByStudentId(studentId);
    }
}

