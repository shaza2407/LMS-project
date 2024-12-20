package com.example.lms.controller;

import com.example.lms.model.Assessment;
import com.example.lms.model.Submission;
import com.example.lms.service.AssessmentService;
import com.example.lms.service.SubmissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        List<Assessment> assessments = assessmentService.getAssessmentsForStudent(studentId, courseId);
        return ResponseEntity.ok(assessments);
    }

    @PostMapping("/{studentId}/assessments/{assessmentId}/submit")
    public ResponseEntity<String> submitAssignment(
            @PathVariable Long studentId,
            @PathVariable Long assessmentId,
            @RequestParam String filePath) {
        String response = submissionService.submitAssignment(studentId, assessmentId, filePath);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{studentId}/grades")
    public ResponseEntity<List<Submission>> getGrades(@PathVariable Long studentId) {
        List<Submission> submissions = submissionService.getSubmissionsByStudent(studentId);
        return ResponseEntity.ok(submissions);
    }
}

