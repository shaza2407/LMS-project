package com.example.lms.service;
import com.example.lms.model.Assessment;
import com.example.lms.model.Course;
import com.example.lms.repository.AssessmentRepository;
import com.example.lms.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.lms.repository.EnrollmentRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AssessmentService {
    private final AssessmentRepository assessmentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    // Create an assessment
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
        boolean isEnrolled = enrollmentRepository.existsByCourseIdAndStudentId(courseId, studentId);
        if (!isEnrolled) {
            throw new RuntimeException("Student is not enrolled in this course");
        }
        return assessmentRepository.findByCourseId(courseId);
    }

    public void deleteAssessment(Long id) {
        assessmentRepository.deleteById(id);
    }
    public Assessment getAssessmentById(Long id) {
        return assessmentRepository.getById(id);
    }
    public List<Assessment> getAllAssessments() {
        return assessmentRepository.findAll();
    }
    public String getTrackingData(Long courseId) {
        // Logic to fetch attendance and scores
        return "Attendance and scores for course: " + courseId;
    }

}

