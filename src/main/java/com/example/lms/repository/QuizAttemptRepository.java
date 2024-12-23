package com.example.lms.repository;
import com.example.lms.model.Quiz;
import com.example.lms.model.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    List<QuizAttempt> findByQuiz(Quiz quiz);
}
