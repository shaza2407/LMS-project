package com.example.lms.repository;

import com.example.lms.model.Assessment;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository

public interface AssessmentRepository {
    List<Assessment> findAll();
    Assessment findById(Long id);
    void save(Assessment assessment);
    void deleteById(Long id);
}
