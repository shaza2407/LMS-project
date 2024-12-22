package com.example.lms.controller;

import com.example.lms.model.Assessment;
import com.example.lms.model.Submission;
import com.example.lms.service.AssessmentService;
import com.example.lms.service.SubmissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/student")
@AllArgsConstructor
public class StudentController {
    private final AssessmentService assessmentService;
    private final SubmissionService submissionService;

    @GetMapping("/{studentId}/courses/{courseId}/assessments")
    public ResponseEntity<List<Assessment>> getAssessments(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        try {
            // Call the service to get assessments
            List<Assessment> assessments = assessmentService.getAssessmentsForStudent(studentId, courseId);

            // Return the assessments in the response
            return ResponseEntity.ok(assessments);
        } catch (IllegalArgumentException e) {
            // Handle the case when the student is not enrolled
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (NoSuchElementException e) {
            // Handle the case when no assessments are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


//    @PostMapping("/{studentId}/assessments/{assessmentId}/submit")
//    public ResponseEntity<String> submitAssignment(
//            @PathVariable Long studentId,
//            @PathVariable Long assessmentId,
//            @RequestParam String filePath) {
//        String response = submissionService.submitAssignment(studentId, assessmentId, filePath);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/{studentId}/assessments/{assessmentId}/submit")
    public ResponseEntity<String> submitAssignment(
            @PathVariable Long studentId,
            @PathVariable Long assessmentId,
            @RequestParam("file") MultipartFile file) {
        String response = submissionService.submitAssignment(studentId, assessmentId, file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{studentId}/grades")
    public ResponseEntity<List<Submission>> getGrades(@PathVariable Long studentId) {
        List<Submission> submissions = submissionService.getSubmissionsByStudent(studentId);
        return ResponseEntity.ok(submissions);
    }
}

