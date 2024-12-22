package com.example.lms.repository;

import com.example.lms.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionBankRepository extends JpaRepository<Question, Long> {
}
