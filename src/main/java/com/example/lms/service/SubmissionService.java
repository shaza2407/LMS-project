package com.example.lms.service;

import com.example.lms.model.*;
import com.example.lms.repository.AssessmentRepository;
import com.example.lms.repository.SubmissionRepository;
import com.example.lms.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SubmissionService
{
    private final SubmissionRepository submissionRepository;
    private final AssessmentRepository assessmentRepository;
    private final UserRepository userRepository;

    @Autowired
    NotificationService notificationService;

    // View submissions for an assessment(by instructor)
    public List<Submission> getSubmissionsForAssessment(Long assessmentId)
    {
        return submissionRepository.findByAssessmentId(assessmentId);
    }

    // Grade a submission
    public Submission gradeSubmission(Long submissionId, Double grade, String feedback)
    {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
        submission.setGrade(grade);
        submission.setFeedback(feedback);
        User STUDENT = submission.getStudent();
        if (STUDENT != null)
        {
            String message = "Student " + STUDENT.getName() + " grade in " + submission.getAssessment().getTitle() + " is " + submission.getGrade();
            Notification notification = new Notification(
                    message,
                    LocalDate.now().atStartOfDay(), // Timestamp
                    STUDENT // Sender
            );
            notificationService.addNotification(notification); // Save notification
        }

        String body = "Your grade in " + submission.getAssessment().getTitle() + " is " + submission.getGrade();
        notificationService.setNotificationsEmail(STUDENT.getEmail(), "Grades", body);


        return submissionRepository.save(submission);

    }


    // By student
    public String submitAssignment(Long studentId, Long assessmentId, MultipartFile file)
    {
        try
        {
            System.out.println("Submitting assignment for studentId: " + studentId);
            System.out.println("For assessmentId: " + assessmentId);
            System.out.println("File name: " + file.getOriginalFilename());
            Assessment assessment = assessmentRepository.findById(assessmentId)
                    .orElseThrow(() -> new RuntimeException("Assessment not found"));
            System.out.println("Assessment fetched successfully.");
            if (assessment.getType() != AssessmentType.ASSIGNMENT)
            {
                throw new RuntimeException("This assessment is not an assignment");
            }
            System.out.println("Assessment type is valid.");
            User student = userRepository.findById(String.valueOf(studentId))
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            System.out.println("Student fetched successfully.");

            Submission submission = new Submission();
            submission.setStudent(student);
            submission.setAssessment(assessment);
            submission.setFilePath(saveFile(file)); // Ensure this method works correctly
            submission.setSubmittedOn(LocalDateTime.now());
            System.out.println("Submission object created successfully.");

            submissionRepository.save(submission);
            System.out.println("Submission saved successfully.");
            return "Assignment submitted successfully!";
        } catch (Exception e)
        {
            System.err.println("Error during assignment submission: " + e.getMessage());
            throw new RuntimeException("Error during assignment submission: " + e.getMessage(), e);
        }
    }


    private String saveFile(MultipartFile file)
    {
        try
        {
            String fileName = file.getOriginalFilename();
            Path path = Paths.get("uploads/" + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return path.toString();
        } catch (IOException e)
        {
            throw new RuntimeException("File upload failed", e);
        }
    }


    public List<Submission> getSubmissionsByStudent(Long studentId)
    {
        return submissionRepository.findByStudentId(studentId);
    }
}

