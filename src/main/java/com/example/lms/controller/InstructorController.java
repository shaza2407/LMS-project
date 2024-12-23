package com.example.lms.controller;

import com.example.lms.model.*;
import com.example.lms.service.AssessmentService;
import com.example.lms.service.AttendanceService;
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
    private final AttendanceService attendanceService;

    private final CourseService courseService;
//    @PostMapping("/{instructorId}/courses/{courseId}/assessments")
//    public ResponseEntity<Assessment> createAssessment(
//            @PathVariable Long instructorId,
//            @PathVariable Long courseId,
//            @RequestBody Assessment assessment) {
//        Assessment createdAssessment = assessmentService.createAssessment(instructorId, courseId, assessment);
//        return ResponseEntity.ok(createdAssessment);
//    }
    @RolesAllowed({"INSTRUCTOR"})
    @GetMapping("/assessments/{assessmentId}/submissions")
    public ResponseEntity<List<Submission>> getSubmissions(@PathVariable Long assessmentId) {
        List<Submission> submissions = submissionService.getSubmissionsForAssessment(assessmentId);
        return ResponseEntity.ok(submissions);
    }

    @RolesAllowed({"INSTRUCTOR"})
    @PutMapping("/submissions/{submissionId}/grade")
    public ResponseEntity<Submission> gradeSubmission(
            @PathVariable Long submissionId,
            @RequestParam Double grade,
            @RequestParam String feedback) {
        Submission gradedSubmission = submissionService.gradeSubmission(submissionId, grade, feedback);
        return ResponseEntity.ok(gradedSubmission);
    }

    //@RolesAllowed({"INSTRUCTOR"})
    @GetMapping("/courses/{courseId}/attendance")
    public ResponseEntity<List<Attendance>> getAttendance(@PathVariable Long courseId) {
        List<Attendance> attendanceRecords = attendanceService.getAttendanceForCourse(courseId);
        return ResponseEntity.ok(attendanceRecords);
    }

    // Endpoint for adding a question bank to a course
    @PostMapping("/{courseId}/content/questionbank")
    public ResponseEntity<Question> addQuestionToCourse(
            @PathVariable Long courseId,
            @RequestBody Question questionBank) {
        Question addedQuestion = courseService.addQuestionToCourse(courseId, questionBank);
        return ResponseEntity.ok(addedQuestion);
    }

    // Endpoint for adding a quiz to a course
    @PostMapping("/{courseId}/content/quizzes")
    public ResponseEntity<Quiz> addQuizToCourse(
            @PathVariable Long courseId,
            @RequestBody Quiz quiz,
            @RequestParam int numberOfQuestions) {
        Quiz addedQuiz = courseService.addQuizToCourse(courseId, quiz, numberOfQuestions);
        return ResponseEntity.ok(addedQuiz);
    }


}

