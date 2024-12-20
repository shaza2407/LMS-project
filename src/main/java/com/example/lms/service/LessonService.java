package com.example.lms.service;

import com.example.lms.model.Lesson;
import com.example.lms.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    public String generateOtpForLesson(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        String otp = String.valueOf((int) (Math.random() * 9000) + 1000); // Generate 4-digit OTP
        lesson.setOtp(otp); // Save OTP in the lesson entity
        lessonRepository.save(lesson);

        return otp;
    }
}

