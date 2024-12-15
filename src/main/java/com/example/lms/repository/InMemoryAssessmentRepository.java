package com.example.lms.repository;

import com.example.lms.model.Assessment;
import java.util.ArrayList;
import java.util.List;

public class InMemoryAssessmentRepository implements AssessmentRepository {
    private final List<Assessment> assessments = new ArrayList<>();

    @Override
    public List<Assessment> findAll() {
        return assessments;
    }

    @Override
    public Assessment findById(Long id) {
        return assessments.stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void save(Assessment assessment) {
        assessments.add(assessment);
    }

    @Override
    public void deleteById(Long id) {
        assessments.removeIf(a -> a.getId().equals(id));
    }
}
