package com.example.lms.controller;

import com.example.lms.model.Course;
import com.example.lms.service.AttendanceService;
import com.example.lms.service.CourseService;
import com.example.lms.service.EnrollmentService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private AttendanceService attendanceService;

    // Endpoint for enrolling a student in a course
    @PostMapping("/{courseId}/enroll")
    @RolesAllowed({"STUDENT"})
    public ResponseEntity<String> enrollStudent(
            @PathVariable Long courseId,
            @RequestParam Long studentId) {
        String response = enrollmentService.enrollStudent(courseId, studentId);
        return ResponseEntity.ok(response);
    }

    // Endpoint for attending a lesson via OTP
    @PostMapping("/{courseId}/lessons/{lessonId}/attend")
    public ResponseEntity<String> attendLesson(
            @PathVariable Long courseId,
            @PathVariable Long lessonId,
            @RequestParam Long studentId,
            @RequestParam String otp) {
        String response = attendanceService.attendLesson(lessonId, studentId, otp);
        return ResponseEntity.ok(response);
    }
}

