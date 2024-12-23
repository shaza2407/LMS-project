package com.example.lms.controller;

import com.example.lms.model.Assessment;
import com.example.lms.model.Submission;
import com.example.lms.service.AssessmentService;
import com.example.lms.service.SubmissionService;
import jakarta.annotation.security.RolesAllowed;
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

    //get assignment
    @RolesAllowed({"STUDENT"})
    @GetMapping("/{studentId}/courses/{courseId}/assessments")
    public ResponseEntity<List<Assessment>> getAssessments(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        try {
            List<Assessment> assessments = assessmentService.getAssessmentsForStudent(studentId, courseId);
            return ResponseEntity.ok(assessments);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    //submit assignment
    @RolesAllowed({"STUDENT"})
    @PostMapping("/assessments/submit")
    public ResponseEntity<String> submitAssignment(
            @RequestParam Long studentId,
            @RequestParam Long assessmentId,
            @RequestParam("File") MultipartFile file) {
        try {
            String response = submissionService.submitAssignment(studentId, assessmentId, file);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


    //get assignment grade
    @RolesAllowed({"STUDENT"})
    @GetMapping("/{studentId}/grades")
    public ResponseEntity<List<Submission>> getGrades(@PathVariable Long studentId) {
        List<Submission> submissions = submissionService.getSubmissionsByStudent(studentId);
        return ResponseEntity.ok(submissions);
    }


}

