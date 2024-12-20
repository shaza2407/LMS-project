package com.example.lms.controller;

import com.example.lms.service.AttendanceService;
import com.example.lms.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    @Autowired
    private LessonService lessonService;

    @Autowired
    private AttendanceService attendanceService;

    // Endpoint for generating OTP (Instructor role)
    @PostMapping("/generate-otp/{lessonId}")
    public ResponseEntity<String> generateOtp(@PathVariable Long lessonId) {
        String otp = lessonService.generateOtpForLesson(lessonId);
        return ResponseEntity.ok("OTP for the lesson: " + otp);
    }

    // Endpoint for attending a lesson (Student role)
    @PostMapping("/attend/{lessonId}")
    public ResponseEntity<String> attendLesson(
            @PathVariable Long lessonId,
            @RequestParam Long studentId,
            @RequestParam String otp) {
        String response = attendanceService.attendLesson(lessonId, studentId, otp);
        return ResponseEntity.ok(response);
    }
}