package com.example.lms.service;
import com.example.lms.model.Assessment;
import com.example.lms.model.Course;
import com.example.lms.model.Question;
import com.example.lms.repository.AssessmentRepository;
import com.example.lms.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.lms.repository.EnrollmentRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AssessmentService {
    private final AssessmentRepository assessmentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    // Create an assessment
    public Assessment createAssessment(Long instructorId, Long courseId, Assessment assessment) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new IllegalArgumentException("Instructor does not own this course");
        }

        // Associate the course with the assessment
        assessment.setCourse(course);

        // Save and return the created assessment
        return assessmentRepository.save(assessment);
    }


    public Assessment createQuiz(Long instructorId, Long courseId, Assessment assessment, List<Long> questionIds, int numQuestions) {
        // Fetch the course and validate the instructor
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new IllegalArgumentException("Instructor does not own this course");
        }

        // Fetch questions from the course's question bank
        List<Question> selectedQuestions = course.getQuestionBank().stream()
                .filter(question -> questionIds.contains(question.getId()))
                .collect(Collectors.toList());
        // Check if we have enough questions for the quiz
        if (selectedQuestions.size() < numQuestions) {
            throw new IllegalArgumentException("Not enough questions in the bank to create the quiz");
        }

        // Shuffle the questions to randomize their order
        Collections.shuffle(selectedQuestions);
        // Select the specified number of questions
        List<Question> randomizedQuestions = selectedQuestions.subList(0, numQuestions);
        // Associate the course with the quiz and set the selected/randomized questions
        assessment.setCourse(course);
        assessment.setQuestions(randomizedQuestions);
        // Save the created quiz (assessment) to the database
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

