package com.example.lms.service;

import com.example.lms.model.Assessment;
import com.example.lms.repository.AssessmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class AssessmentServiceTest {

    private AssessmentRepository assessmentRepository;
    private AssessmentService assessmentService;

    @BeforeEach
    void setUp() {
        assessmentRepository = Mockito.mock(AssessmentRepository.class);
        assessmentService = new AssessmentService(assessmentRepository);
    }

    @Test
    void testCreateQuiz() {
        Assessment quiz = new Assessment();
        quiz.setType("QUIZ");
        quiz.setContent("Sample Quiz Content");

        assessmentService.createQuiz(1L, quiz);

        verify(assessmentRepository, times(1)).save(quiz);
    }

    @Test
    void testCreateAssignment() {
        Assessment assignment = new Assessment();
        assignment.setType("ASSIGNMENT");
        assignment.setContent("Sample Assignment Content");

        assessmentService.createAssignment(1L, assignment);

        verify(assessmentRepository, times(1)).save(assignment);
    }

    @Test
    void testGradeQuiz() {
        Assessment quiz = new Assessment();
        quiz.setId(1L);
        quiz.setType("QUIZ");
        quiz.setGrade(null);

        when(assessmentRepository.findById(1L)).thenReturn(quiz);

        assessmentService.gradeQuiz(1L, "A");

        verify(assessmentRepository, times(1)).save(quiz);
        assert quiz.getGrade().equals("A");
    }
}
