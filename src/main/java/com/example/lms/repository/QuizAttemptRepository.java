package com.example.lms.repository;

import com.example.lms.model.Course;
import com.example.lms.model.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
}
