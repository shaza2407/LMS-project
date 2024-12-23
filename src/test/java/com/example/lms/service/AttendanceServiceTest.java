package com.example.lms.service;

import com.example.lms.model.Attendance;
import com.example.lms.model.Lesson;
import com.example.lms.model.User;
import com.example.lms.repository.AttendanceRepository;
import com.example.lms.repository.LessonRepository;
import com.example.lms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AttendanceServiceTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AttendanceService attendanceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void attendLesson_ShouldMarkAttendance_WhenValidDetailsProvided() {
        Long lessonId = 1L;
        Long studentId = 1L;
        String otp = "123456";
        Lesson lesson = new Lesson();
        lesson.setId(lessonId);
        lesson.setOtp(otp);
        User student = new User();
        student.setId(studentId);
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lesson));
        when(userRepository.findById(String.valueOf(studentId))).thenReturn(Optional.of(student));
        when(attendanceRepository.existsByLessonAndStudent(lesson, student)).thenReturn(false);
        String result = attendanceService.attendLesson(lessonId, studentId, otp);
        assertEquals("Attendance marked successfully.", result);
        verify(attendanceRepository, times(1)).save(any(Attendance.class));
    }

    @Test
    void attendLesson_ShouldThrowException_WhenLessonNotFound() {
        Long lessonId = 1L;
        Long studentId = 1L;
        String otp = "123456";
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                attendanceService.attendLesson(lessonId, studentId, otp));
        assertEquals("Lesson not found", exception.getMessage());
        verify(attendanceRepository, never()).save(any(Attendance.class));
    }

    @Test
    void attendLesson_ShouldThrowException_WhenStudentNotFound() {
        Long lessonId = 1L;
        Long studentId = 1L;
        String otp = "123456";
        Lesson lesson = new Lesson();
        lesson.setId(lessonId);
        lesson.setOtp(otp);
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lesson));
        when(userRepository.findById(String.valueOf(studentId))).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                attendanceService.attendLesson(lessonId, studentId, otp));
        assertEquals("Student not found", exception.getMessage());
        verify(attendanceRepository, never()).save(any(Attendance.class));
    }

    @Test
    void attendLesson_ShouldReturnInvalidOtp_WhenOtpDoesNotMatch() {
        Long lessonId = 1L;
        Long studentId = 1L;
        String otp = "123456";
        Lesson lesson = new Lesson();
        lesson.setId(lessonId);
        lesson.setOtp("654321");
        User student = new User();
        student.setId(studentId);
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lesson));
        when(userRepository.findById(String.valueOf(studentId))).thenReturn(Optional.of(student));
        String result = attendanceService.attendLesson(lessonId, studentId, otp);
        assertEquals("Invalid OTP!", result);
        verify(attendanceRepository, never()).save(any(Attendance.class));
    }

    @Test
    void attendLesson_ShouldReturnAlreadyMarked_WhenAttendanceExists() {
        Long lessonId = 1L;
        Long studentId = 1L;
        String otp = "123456";
        Lesson lesson = new Lesson();
        lesson.setId(lessonId);
        lesson.setOtp(otp);
        User student = new User();
        student.setId(studentId);
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lesson));
        when(userRepository.findById(String.valueOf(studentId))).thenReturn(Optional.of(student));
        when(attendanceRepository.existsByLessonAndStudent(lesson, student)).thenReturn(true);
        String result = attendanceService.attendLesson(lessonId, studentId, otp);
        assertEquals("Attendance already marked for this lesson.", result);
        verify(attendanceRepository, never()).save(any(Attendance.class));
    }

    @Test
    void getAttendanceForCourse_ShouldReturnAttendanceList_WhenCourseExists() {
        Long courseId = 1L;
        List<Attendance> attendances = new ArrayList<>();
        attendances.add(new Attendance());
        when(attendanceRepository.findByLessonCourseId(courseId)).thenReturn(attendances);
        List<Attendance> result = attendanceService.getAttendanceForCourse(courseId);
        assertNotNull(result);
        assertEquals(attendances.size(), result.size());
        verify(attendanceRepository, times(1)).findByLessonCourseId(courseId);
    }

    @Test
    void getAttendanceForCourse_ShouldReturnEmptyList_WhenNoAttendanceFound() {
        Long courseId = 1L;
        when(attendanceRepository.findByLessonCourseId(courseId)).thenReturn(new ArrayList<>());
        List<Attendance> result = attendanceService.getAttendanceForCourse(courseId);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(attendanceRepository, times(1)).findByLessonCourseId(courseId);
    }
}
