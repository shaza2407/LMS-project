package com.example.lms.service;

import com.example.lms.model.Question;
import com.example.lms.model.Quiz;
import com.example.lms.model.QuizAttempt;
import com.example.lms.model.User;
import com.example.lms.repository.QuizAttemptRepository;
import com.example.lms.repository.QuizRepository;
import com.example.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Autowired
    private UserRepository userRepository;


    public Quiz getQuizById(Long quizId) {
        return quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
    }


    public String submitQuiz(Long studentId, Long quizId, Map<Long, String> answers) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
        User student = userRepository.findById(String.valueOf(studentId))
                .orElseThrow(() -> new RuntimeException("Student not found"));

        int score = 0;
        for (Question question : quiz.getQuestions()) {
            String correctAnswer = question.getAnswer();
            String studentAnswer = answers.get(question.getId());
            if (correctAnswer.equalsIgnoreCase(studentAnswer)) {
                score++;
            }
        }
        QuizAttempt attempt = new QuizAttempt();
        attempt.setQuiz(quiz);
        attempt.setStudent(student);
        attempt.setStudentAnswers(answers);
        attempt.setScore(score);
        attempt.setAttemptDate(LocalDateTime.now());
        quizAttemptRepository.save(attempt);
        return "Quiz submitted successfully! Your score is: " + score + "/" + quiz.getQuestions().size();
    }


    public List<QuizAttempt> getQuizSubmissionsForQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
        return quizAttemptRepository.findByQuiz(quiz);
    }

}
