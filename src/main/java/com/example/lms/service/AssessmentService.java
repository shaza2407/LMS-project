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
        // Check if the student is enrolled in the course
        if (!enrollmentRepository.existsByCourseIdAndStudentId(courseId, studentId)) {
            throw new IllegalArgumentException("Student is not enrolled in this course.");
        }

        // Fetch assessments associated with the course for the student
        List<Assessment> assessments = assessmentRepository.findByCourseId(courseId);

        // Optional: Ensure assessments are filtered for the specific student, if needed
        if (assessments.isEmpty()) {
            throw new NoSuchElementException("No assessments found for this course.");
        }

        return assessments;
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

