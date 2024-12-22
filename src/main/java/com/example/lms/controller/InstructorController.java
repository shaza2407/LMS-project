package com.example.lms.controller;

import com.example.lms.model.Assessment;
import com.example.lms.model.Course;
import com.example.lms.model.Submission;
import com.example.lms.service.AssessmentService;
import com.example.lms.service.CourseService;
import com.example.lms.service.SubmissionService;
import jakarta.annotation.security.RolesAllowed;
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
    private final CourseService courseService;

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
    @RolesAllowed({"INSTRUCTOR"})
    @PostMapping("/addCourse")
    public ResponseEntity<Course> addCourse(
            @RequestParam Long instructorId,
            @RequestBody Course course) {
        Course createdCourse = courseService.addCourse(instructorId, course);
        return ResponseEntity.ok(createdCourse);
    }


}

