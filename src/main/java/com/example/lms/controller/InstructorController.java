package com.example.lms.controller;
import com.example.lms.model.*;
import com.example.lms.service.AssessmentService;
import com.example.lms.service.CourseService;
import com.example.lms.service.SubmissionService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/instructor")
@AllArgsConstructor
public class InstructorController {
    private final AssessmentService assessmentService;
    private final SubmissionService submissionService;
    private final CourseService courseService;

    //add course
    @RolesAllowed({"INSTRUCTOR"})
    @PostMapping("/addCourse")
    public ResponseEntity<Course> addCourse(
            @RequestParam Long instructorId,
            @RequestBody Course course) {
        Course createdCourse = courseService.addCourse(instructorId, course);
        return ResponseEntity.ok(createdCourse);
    }


    //create assessment
    @RolesAllowed({"INSTRUCTOR"})
    @PostMapping("/courses/assessments")
    public ResponseEntity<Assessment> createAssessment(
            @RequestParam Long instructorId,
            @RequestParam Long courseId,
            @RequestBody Assessment assessment) {
        try {
            Assessment createdAssessment = assessmentService.createAssessment(instructorId, courseId, assessment);
            return ResponseEntity.ok(createdAssessment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Handle validation errors
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Handle unexpected errors
        }
    }


    //create quiz
    @RolesAllowed({"INSTRUCTOR"})
    @PostMapping("/courses/quiz")
    public ResponseEntity<Assessment> createQuiz(
            @RequestParam Long instructorId,
            @RequestParam Long courseId,
            @RequestBody QuizRequest quizRequest) {
        try {
            Assessment createdQuiz = assessmentService.createQuiz(
                    instructorId,
                    courseId,
                    quizRequest.getAssessment(),
                    quizRequest.getQuestionIds(),
                    quizRequest.getNumQuestions()
            );
            return ResponseEntity.ok(createdQuiz);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }


    //give grade to assignment
    @RolesAllowed({"INSTRUCTOR"})
    @PutMapping("/submissions/{submissionId}/grade")
    public ResponseEntity<Submission> gradeSubmission(
            @PathVariable Long submissionId,
            @RequestParam Double grade,
            @RequestParam String feedback) {
        Submission gradedSubmission = submissionService.gradeSubmission(submissionId, grade, feedback);
        return ResponseEntity.ok(gradedSubmission);
    }



    //track assignment submissions
    @RolesAllowed({"INSTRUCTOR"})
    @GetMapping("/assessments/{assessmentId}/submissions")
    public ResponseEntity<List<SubmissionSummary>> getSubmissions(@PathVariable Long assessmentId) {
        List<Submission> submissions = submissionService.getSubmissionsForAssessment(assessmentId);

        List<SubmissionSummary> submissionSummaries = submissions.stream()
                .map(submission -> new SubmissionSummary(
                        submission.getId(),
                        submission.getStudent().getId(),
                        submission.getSubmittedOn(),
                        submission.getGrade()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(submissionSummaries);
    }





}

