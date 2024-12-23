package com.example.lms.controller;

import com.example.lms.model.Quiz;
import com.example.lms.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/{quizId}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable Long quizId) {
        Quiz quiz = quizService.getQuizById(quizId);
        return ResponseEntity.ok(quiz);
    }

    @PostMapping("/{quizId}/submit")
    public ResponseEntity<String> submitQuiz(
            @PathVariable Long quizId,
            @RequestParam Long studentId,
            @RequestBody Map<Long, String> answers) {
        String response = quizService.submitQuiz(studentId, quizId, answers);
        return ResponseEntity.ok(response);
    }
}
