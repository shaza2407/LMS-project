package com.example.lms.repository;

import com.example.lms.model.Assessment;
import java.util.List;

public interface AssessmentRepository {
    List<Assessment> findAll();
    Assessment findById(Long id);
    void save(Assessment assessment);
    void deleteById(Long id);
}
