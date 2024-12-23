package com.example.lms.service;
import com.example.lms.model.Assessment;
import com.example.lms.model.Course;
import com.example.lms.repository.AssessmentRepository;
import com.example.lms.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.lms.repository.EnrollmentRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class AssessmentService {
    private final AssessmentRepository assessmentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    // Create an assignment
    public Assessment createAssessment(Long instructorId, Long courseId, Assessment assessment) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new RuntimeException("Instructor does not own this course");
        }
        assessment.setCourse(course);
        return assessmentRepository.save(assessment);
    }

    // Get assessments for a student's course
    public List<Assessment> getAssessmentsForStudent(Long studentId, Long courseId) {
        if (!enrollmentRepository.existsByCourseIdAndStudentId(courseId, studentId)) {
            throw new IllegalArgumentException("Student is not enrolled in this course.");
        }
        List<Assessment> assessments = assessmentRepository.findByCourseId(courseId);
        if (assessments.isEmpty()) {
            throw new NoSuchElementException("No assessments found for this course.");
        }
        return assessments;
    }

}

