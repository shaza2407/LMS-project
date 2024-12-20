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
@RequestMapping("/api/instructor")
@AllArgsConstructor
public class InstructorController {
    private final AssessmentService assessmentService;
    private final SubmissionService submissionService;

    @PostMapping("/{instructorId}/courses/{courseId}/assessments")
    public ResponseEntity<Assessment> createAssessment(
            @PathVariable Long instructorId,
            @PathVariable Long courseId,
            @RequestBody Assessment assessment) {
        Assessment createdAssessment = assessmentService.createAssessment(instructorId, courseId, assessment);
        return ResponseEntity.ok(createdAssessment);
    }

    @GetMapping("/assessments/{assessmentId}/submissions")
    public ResponseEntity<List<Submission>> getSubmissions(@PathVariable Long assessmentId) {
        List<Submission> submissions = submissionService.getSubmissionsForAssessment(assessmentId);
        return ResponseEntity.ok(submissions);
    }

    @PutMapping("/submissions/{submissionId}/grade")
    public ResponseEntity<Submission> gradeSubmission(
            @PathVariable Long submissionId,
            @RequestParam Double grade,
            @RequestParam String feedback) {
        Submission gradedSubmission = submissionService.gradeSubmission(submissionId, grade, feedback);
        return ResponseEntity.ok(gradedSubmission);
    }
}

