package com.example.lms.controller;

import com.example.lms.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/{courseId}")
    public ResponseEntity<String> enrollCourse(
            @PathVariable Long courseId,
            @RequestParam Long studentId) {
        String response = enrollmentService.enrollStudent(courseId, studentId);
        return ResponseEntity.ok(response);
    }
}

