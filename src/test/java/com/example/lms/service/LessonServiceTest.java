package com.example.lms.service;

import com.example.lms.model.Lesson;
import com.example.lms.repository.LessonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LessonServiceTest {

    @InjectMocks
    private LessonService lessonService;

    @Mock
    private LessonRepository lessonRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateOtpForLesson_ShouldGenerateOtp_WhenLessonExists() {
        Long lessonId = 1L;
        Lesson lesson = new Lesson();
        lesson.setId(lessonId);
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lesson));
        String otp = lessonService.generateOtpForLesson(lessonId);
        assertNotNull(otp);
        assertEquals(4, otp.length());
        assertTrue(otp.matches("\\d{4}")); // Ensure OTP is a 4-digit number
        verify(lessonRepository, times(1)).save(lesson);
        assertEquals(otp, lesson.getOtp());
    }

    @Test
    void generateOtpForLesson_ShouldThrowException_WhenLessonNotFound() {
        Long lessonId = 1L;
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () ->
                lessonService.generateOtpForLesson(lessonId));
        assertEquals("Lesson not found", exception.getMessage());
        verify(lessonRepository, never()).save(any(Lesson.class));
    }
}
