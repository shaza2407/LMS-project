package com.example.lms.controller;

import com.example.lms.model.Quiz;
import com.example.lms.service.QuizService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/quizes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    //to get quiz questions
    @RolesAllowed({"STUDENT"})
    @GetMapping("/{quizId}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable Long quizId) {
        Quiz quiz = quizService.getQuizById(quizId);
        return ResponseEntity.ok(quiz);
    }

    //to submit quiz answers
    @RolesAllowed({"STUDENT"})
    @PostMapping("/{quizId}/submit")
    public ResponseEntity<String> submitQuiz(
            @PathVariable Long quizId,
            @RequestParam Long studentId,
            @RequestBody Map<Long, String> answers) {
        String response = quizService.submitQuiz(studentId, quizId, answers);
        return ResponseEntity.ok(response);
    }
}
