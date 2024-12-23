package com.example.lms.service;

import com.example.lms.model.Assessment;
import com.example.lms.model.Course;
import com.example.lms.model.User;
import com.example.lms.repository.AssessmentRepository;
import com.example.lms.repository.CourseRepository;
import com.example.lms.repository.EnrollmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssessmentServiceTest {

    @Mock
    private AssessmentRepository assessmentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private AssessmentService assessmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAssessment_ShouldReturnSavedAssessment_WhenInstructorOwnsCourse() {
        Long instructorId = 1L;
        Long courseId = 1L;
        Assessment assessment = new Assessment();
        Course course = new Course();
        course.setId(courseId);
        User instructor = new User();
        instructor.setId(instructorId);
        course.setInstructor(instructor);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(assessmentRepository.save(assessment)).thenReturn(assessment);
        Assessment result = assessmentService.createAssessment(instructorId, courseId, assessment);
        assertNotNull(result);
        assertEquals(course, assessment.getCourse());
        verify(courseRepository, times(1)).findById(courseId);
        verify(assessmentRepository, times(1)).save(assessment);
    }

    @Test
    void createAssessment_ShouldThrowException_WhenInstructorDoesNotOwnCourse() {
        Long instructorId = 1L;
        Long courseId = 1L;
        Assessment assessment = new Assessment();
        Course course = new Course();
        course.setId(courseId);
        User instructor = new User();
        instructor.setId(2L); // Different instructor
        course.setInstructor(instructor);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                assessmentService.createAssessment(instructorId, courseId, assessment));
        assertEquals("Instructor does not own this course", exception.getMessage());
        verify(courseRepository, times(1)).findById(courseId);
        verify(assessmentRepository, never()).save(assessment);
    }

    @Test
    void getAssessmentsForStudent_ShouldReturnAssessments_WhenStudentIsEnrolled() {
        Long studentId = 1L;
        Long courseId = 1L;
        List<Assessment> assessments = new ArrayList<>();
        assessments.add(new Assessment());
        when(enrollmentRepository.existsByCourseIdAndStudentId(courseId, studentId)).thenReturn(true);
        when(assessmentRepository.findByCourseId(courseId)).thenReturn(assessments);
        List<Assessment> result = assessmentService.getAssessmentsForStudent(studentId, courseId);
        assertNotNull(result);
        assertEquals(assessments.size(), result.size());
        verify(enrollmentRepository, times(1)).existsByCourseIdAndStudentId(courseId, studentId);
        verify(assessmentRepository, times(1)).findByCourseId(courseId);
    }

    @Test
    void getAssessmentsForStudent_ShouldThrowException_WhenStudentIsNotEnrolled() {
        Long studentId = 1L;
        Long courseId = 1L;
        when(enrollmentRepository.existsByCourseIdAndStudentId(courseId, studentId)).thenReturn(false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                assessmentService.getAssessmentsForStudent(studentId, courseId));
        assertEquals("Student is not enrolled in this course.", exception.getMessage());
        verify(enrollmentRepository, times(1)).existsByCourseIdAndStudentId(courseId, studentId);
        verify(assessmentRepository, never()).findByCourseId(courseId);
    }

    @Test
    void getAssessmentsForStudent_ShouldThrowException_WhenNoAssessmentsFound() {
        Long studentId = 1L;
        Long courseId = 1L;
        when(enrollmentRepository.existsByCourseIdAndStudentId(courseId, studentId)).thenReturn(true);
        when(assessmentRepository.findByCourseId(courseId)).thenReturn(new ArrayList<>());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                assessmentService.getAssessmentsForStudent(studentId, courseId));
        assertEquals("No assessments found for this course.", exception.getMessage());
        verify(enrollmentRepository, times(1)).existsByCourseIdAndStudentId(courseId, studentId);
        verify(assessmentRepository, times(1)).findByCourseId(courseId);
    }
}
