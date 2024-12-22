package com.example.lms.service;

import com.example.lms.model.Assessment;
import com.example.lms.model.AssessmentType;
import com.example.lms.model.Submission;
import com.example.lms.model.User;
import com.example.lms.repository.AssessmentRepository;
import com.example.lms.repository.SubmissionRepository;
import com.example.lms.repository.UserRepository;
import jakarta.persistence.criteria.Path;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

//// By student
//    public String submitAssignment(Long studentId, Long assessmentId, String filePath) {
//        // Fetch the assessment
//        Assessment assessment = assessmentRepository.findById(assessmentId)
//                .orElseThrow(() -> new RuntimeException("Assessment not found"));
//
//        // Ensure it's an assignment type
//        if (assessment.getType() != AssessmentType.ASSIGNMENT) {
//            throw new RuntimeException("This assessment is not an assignment");
//        }
//
//        // Fetch the student entity
//        User student = userRepository.findById(String.valueOf(studentId))
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//
//        // Create and save the submission
//        Submission submission = new Submission();
//        submission.setStudent(student);
//        submission.setAssessment(assessment);
//        submission.setFilePath(filePath);
//        submission.setSubmittedOn(LocalDateTime.now());
//
//        submissionRepository.save(submission);
//        return "Assignment submitted successfully!";
//    }

    // By student
    public String submitAssignment(Long studentId, Long assessmentId, MultipartFile file) {
        System.out.println("Submitting assignment for studentId: " + studentId);
        System.out.println("For assessmentId: " + assessmentId);
        System.out.println("File name: " + file.getOriginalFilename());

        // Fetch assessment
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        // Ensure it's an assignment type
        if (assessment.getType() != AssessmentType.ASSIGNMENT) {
            throw new RuntimeException("This assessment is not an assignment");
        }

        // Fetch student entity
        User student = userRepository.findById(String.valueOf(studentId))
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Create and save the submission
        Submission submission = new Submission();
        submission.setStudent(student);
        submission.setAssessment(assessment);
        submission.setFilePath(saveFile(file)); // Ensure this method works correctly
        submission.setSubmittedOn(LocalDateTime.now());

        submissionRepository.save(submission);
        return "Assignment submitted successfully!";
    }
    private String saveFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            Path path = (Path) Paths.get("uploads/" + fileName);
            Files.copy((java.nio.file.Path) file.getInputStream(), (java.nio.file.Path) path, StandardCopyOption.REPLACE_EXISTING);
            return path.toString(); // Return the file path or URL
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }
    }
    public List<Submission> getSubmissionsByStudent(Long studentId) {
        return submissionRepository.findByStudentId(studentId);
    }
}

