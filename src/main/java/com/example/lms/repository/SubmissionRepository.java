package com.example.lms.repository;

import com.example.lms.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByAssessmentId(Long assessmentId);
    List<Submission> findByStudentId(Long studentId);
}

