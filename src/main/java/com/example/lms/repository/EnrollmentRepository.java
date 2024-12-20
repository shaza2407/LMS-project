package com.example.lms.repository;

import com.example.lms.model.Course;
import com.example.lms.model.Enrollment;
import com.example.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByCourseAndStudent(Course course, User student);
    List<Enrollment> findByStudent(User student);
    List<Enrollment> findByCourse(Course course);
    boolean existsByCourseIdAndStudentId(Long courseId, Long studentId); // Add this method

}

