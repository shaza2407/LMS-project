package com.example.lms.service;

import com.example.lms.model.Question;
import com.example.lms.model.Quiz;
import com.example.lms.model.QuizAttempt;
import com.example.lms.model.User;
import com.example.lms.repository.QuizAttemptRepository;
import com.example.lms.repository.QuizRepository;
import com.example.lms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizServiceTest {

    @InjectMocks
    private QuizService quizService;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuizAttemptRepository quizAttemptRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void submitQuiz_ShouldCalculateScoreAndSaveAttempt_WhenQuizAndStudentExist() {
        Long quizId = 1L;
        Long studentId = 1L;
        Map<Long, String> answers = Map.of(1L, "Answer1", 2L, "Answer2");
        Quiz quiz = new Quiz();
        quiz.setId(quizId);
        Question question1 = new Question();
        question1.setId(1L);
        question1.setAnswer("Answer1");
        Question question2 = new Question();
        question2.setId(2L);
        question2.setAnswer("Answer2");
        quiz.setQuestions(List.of(question1, question2));
        User student = new User();
        student.setId(Long.valueOf(String.valueOf(studentId)));
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));
        when(userRepository.findById(String.valueOf(studentId))).thenReturn(Optional.of(student));
        String result = quizService.submitQuiz(studentId, quizId, answers);
        assertEquals("Quiz submitted successfully! Your score is: 2/2", result);
        verify(quizAttemptRepository, times(1)).save(any(QuizAttempt.class));
    }

    @Test
    void submitQuiz_ShouldThrowException_WhenQuizNotFound() {
        Long quizId = 1L;
        Long studentId = 1L;
        Map<Long, String> answers = Map.of(1L, "Answer1");
        when(quizRepository.findById(quizId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () ->
                quizService.submitQuiz(studentId, quizId, answers));
        assertEquals("Quiz not found", exception.getMessage());
        verify(quizAttemptRepository, never()).save(any(QuizAttempt.class));
    }

    @Test
    void submitQuiz_ShouldThrowException_WhenStudentNotFound() {
        Long quizId = 1L;
        Long studentId = 1L;
        Map<Long, String> answers = Map.of(1L, "Answer1");
        Quiz quiz = new Quiz();
        quiz.setId(quizId);
        quiz.setQuestions(List.of(new Question()));
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));
        when(userRepository.findById(String.valueOf(studentId))).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () ->
                quizService.submitQuiz(studentId, quizId, answers));
        assertEquals("Student not found", exception.getMessage());
        verify(quizAttemptRepository, never()).save(any(QuizAttempt.class));
    }
}
