package com.example.lms.service;

import com.example.lms.model.Attendance;
import com.example.lms.model.Lesson;
import com.example.lms.model.User;
import com.example.lms.repository.AttendanceRepository;
import com.example.lms.repository.LessonRepository;
import com.example.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private UserRepository userRepository;

    public String attendLesson(Long lessonId, Long studentId, String otp) {
        // Fetch lesson and student
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        User student = userRepository.findById(String.valueOf(studentId))
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Check if the OTP matches
        if (!lesson.getOtp().equals(otp)) {
            return "Invalid OTP!";
        }

        // Check if the student has already attended the lesson
        if (attendanceRepository.existsByLessonAndStudent(lesson, student)) {
            return "Attendance already marked for this lesson.";
        }

        // Mark attendance
        Attendance attendance = new Attendance();
        attendance.setLesson(lesson);
        attendance.setStudent(student);
        attendance.setOtp(otp);
        attendance.setTimestamp(LocalDateTime.now());
        attendance.setAttended(true);

        attendanceRepository.save(attendance);

        return "Attendance marked successfully.";
    }
    public List<Attendance> getAttendanceForCourse(Long courseId) {
        return attendanceRepository.findByLessonCourseId(courseId);
    }
}

