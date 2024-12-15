package com.example.lms.service;

import com.example.lms.model.Assessment;
import com.example.lms.repository.InMemoryAssessmentRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AssessmentServiceTest {
    @Test
    public void testCreateAndRetrieveAssessment() {
        InMemoryAssessmentRepository repository = new InMemoryAssessmentRepository();
        AssessmentService service = new AssessmentService(repository);

        Assessment assessment = new Assessment(1L, "Quiz", "Java Basics", "Quiz on Java Basics", 101L);
        service.createAssessment(assessment);

        List<Assessment> assessments = service.getAllAssessments();
        assertEquals(1, assessments.size());
        assertEquals("Java Basics", assessments.get(0).getTitle());
    }

    @Test
    public void testDeleteAssessment() {
        InMemoryAssessmentRepository repository = new InMemoryAssessmentRepository();
        AssessmentService service = new AssessmentService(repository);

        Assessment assessment = new Assessment(1L, "Quiz", "Java Basics", "Quiz on Java Basics", 101L);
        service.createAssessment(assessment);

        service.deleteAssessment(1L);
        assertTrue(service.getAllAssessments().isEmpty());
    }
}
