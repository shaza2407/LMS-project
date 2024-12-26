package com.example.lms.controller;

import com.example.lms.model.*;
import com.example.lms.service.*;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final EnrollmentService enrollmentService;
    private final CourseService courseService;
    private final UserService userService;

    //to get the list of all courses
    @GetMapping("/getAllCourses")
    @RolesAllowed({"STUDENT"})
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    // get course details by its id
    @GetMapping("/{courseId}/getCourse")
    @RolesAllowed({"STUDENT"})
    public ResponseEntity<Course> getCourseById(@PathVariable Long courseId) {
        Course course = courseService.getCourseById(courseId);
        return course != null ? ResponseEntity.ok(course) : ResponseEntity.notFound().build();
    }

    // enroll student in a course
    @RolesAllowed({"STUDENT"})
    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<String> enrollStudent(@PathVariable Long courseId, @RequestParam Long studentId) {
        String response = enrollmentService.enrollStudent(courseId, studentId);
        return ResponseEntity.ok(response);
    }

    @RolesAllowed({"STUDENT"})
    @GetMapping("/enrolledCourses")
    public ResponseEntity<List<Enrollment>> getEnrolledCourses() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User student = userService.findById(Long.valueOf(name));
        if (student == null || student.getRole() != Role.STUDENT) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<Enrollment> courses = courseService.getEnrolledCoursesByStudentId(student.getId());
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(courses);
    }


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

