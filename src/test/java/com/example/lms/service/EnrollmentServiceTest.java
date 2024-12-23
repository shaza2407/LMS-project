package com.example.lms.service;

import com.example.lms.model.Course;
import com.example.lms.model.Enrollment;
import com.example.lms.model.User;
import com.example.lms.repository.CourseRepository;
import com.example.lms.repository.EnrollmentRepository;
import com.example.lms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnrollmentServiceTest {

    @InjectMocks
    private EnrollmentService enrollmentService;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void enrollStudent_ShouldEnroll_WhenCourseAndStudentExist() {
        Long courseId = 1L;
        Long studentId = 1L;
        Course course = new Course();
        course.setId(courseId);
        User student = new User();
        student.setId(Long.valueOf(String.valueOf(studentId)));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(userRepository.findById(String.valueOf(studentId))).thenReturn(Optional.of(student));
        when(enrollmentRepository.existsByCourseAndStudent(course, student)).thenReturn(false);
        String result = enrollmentService.enrollStudent(courseId, studentId);
        assertEquals("Student successfully enrolled in the course", result);
        verify(enrollmentRepository, times(1)).save(any(Enrollment.class));
    }

    @Test
    void enrollStudent_ShouldReturnAlreadyEnrolled_WhenStudentIsEnrolled() {
        Long courseId = 1L;
        Long studentId = 1L;
        Course course = new Course();
        course.setId(courseId);
        User student = new User();
        student.setId(Long.valueOf(String.valueOf(studentId)));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(userRepository.findById(String.valueOf(studentId))).thenReturn(Optional.of(student));
        when(enrollmentRepository.existsByCourseAndStudent(course, student)).thenReturn(true);
        String result = enrollmentService.enrollStudent(courseId, studentId);
        assertEquals("Student is already enrolled in this course", result);
        verify(enrollmentRepository, never()).save(any(Enrollment.class));
    }

    @Test
    void enrollStudent_ShouldThrowException_WhenCourseNotFound() {
        Long courseId = 1L;
        Long studentId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () ->
                enrollmentService.enrollStudent(courseId, studentId));
        assertEquals("Course not found", exception.getMessage());
    }

    @Test
    void enrollStudent_ShouldThrowException_WhenStudentNotFound() {
        Long courseId = 1L;
        Long studentId = 1L;
        Course course = new Course();
        course.setId(courseId);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(userRepository.findById(String.valueOf(studentId))).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () ->
                enrollmentService.enrollStudent(courseId, studentId));
        assertEquals("Student not found", exception.getMessage());
    }
}
