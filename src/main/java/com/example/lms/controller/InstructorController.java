package com.example.lms.controller;

import com.example.lms.model.*;
import com.example.lms.service.*;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final QuizService quizService;
    private final UserService userService;


    @RolesAllowed({"INSTRUCTOR"})
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getCoursesForAuthenticatedInstructor() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User instructor = userService.findById(Long.valueOf(name));
        if (instructor == null || instructor.getRole() != Role.INSTRUCTOR) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<Course> courses = courseService.getCoursesByInstructorId(instructor.getId());
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(courses);
    }

    //assignment:
    //create assignment
    @RolesAllowed({"INSTRUCTOR"})
    @PostMapping("/{instructorId}/courses/{courseId}/assessments")
    public ResponseEntity<Assessment> createAssessment(
            @PathVariable Long instructorId,
            @PathVariable Long courseId,
            @RequestBody Assessment assessment) {
        Assessment createdAssessment = assessmentService.createAssessment(instructorId, courseId, assessment);
        return ResponseEntity.ok(createdAssessment);
    }

    //grade students assignment
    @RolesAllowed({"INSTRUCTOR"})
    @PutMapping("/submissions/{submissionId}/grade")
    public ResponseEntity<Submission> gradeSubmission(
            @PathVariable Long submissionId,
            @RequestParam Double grade,
            @RequestParam String feedback) {
        Submission gradedSubmission = submissionService.gradeSubmission(submissionId, grade, feedback);
        return ResponseEntity.ok(gradedSubmission);
    }

    //Quizes:
    //add question to course question bank
    @RolesAllowed({"INSTRUCTOR"})
    @PostMapping("/{courseId}/content/questionbank")
    public ResponseEntity<Question> addQuestionToCourse(
            @PathVariable Long courseId,
            @RequestBody Question questionBank) {
        Question addedQuestion = courseService.addQuestionToCourse(courseId, questionBank);
        return ResponseEntity.ok(addedQuestion);
    }

    // create quiz
    @RolesAllowed({"INSTRUCTOR"})
    @PostMapping("/{courseId}/content/quizes")
    public ResponseEntity<Quiz> addQuizToCourse(
            @PathVariable Long courseId,
            @RequestBody Quiz quiz,
            @RequestParam int numberOfQuestions) {
        Quiz addedQuiz = courseService.addQuizToCourse(courseId, quiz, numberOfQuestions);
        return ResponseEntity.ok(addedQuiz);
    }



    //tracking
    //track assignment submissions
    @RolesAllowed({"INSTRUCTOR"})
    @GetMapping("/assessments/{assessmentId}/submissions")
    public ResponseEntity<List<Submission>> getSubmissions(@PathVariable Long assessmentId) {
        List<Submission> submissions = submissionService.getSubmissionsForAssessment(assessmentId);
        return ResponseEntity.ok(submissions);
    }

    //track lessons attendance
    @RolesAllowed({"INSTRUCTOR"})
    @GetMapping("/courses/{courseId}/attendance")
    public ResponseEntity<List<Attendance>> getAttendance(@PathVariable Long courseId) {
        List<Attendance> attendanceRecords = attendanceService.getAttendanceForCourse(courseId);
        return ResponseEntity.ok(attendanceRecords);
    }

    //track quiz submissions
    @RolesAllowed({"INSTRUCTOR"})
    @GetMapping("/quizzes/{quizId}/submissions")
    public ResponseEntity<List<QuizAttempt>> getQuizSubmissions(@PathVariable Long quizId) {
        List<QuizAttempt> quizAttempts = quizService.getQuizSubmissionsForQuiz(quizId);
        return ResponseEntity.ok(quizAttempts);
    }

}

