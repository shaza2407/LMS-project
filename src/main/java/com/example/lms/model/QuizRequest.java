package com.example.lms.model;

import com.example.lms.model.Assessment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizRequest {
    private Assessment assessment;
    private List<Long> questionIds; // IDs of questions from the question bank
    private int numQuestions; // Number of questions to include in the quiz
}
