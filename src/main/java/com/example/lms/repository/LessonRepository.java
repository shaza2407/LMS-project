package com.example.lms.repository;

import com.example.lms.model.Lesson;
import com.example.lms.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    // Find all lessons for a specific course
    List<Lesson> findByCourse(Course course);

    // Find lessons by title containing a keyword (for search functionality)
    List<Lesson> findByTitleContaining(String keyword);

    List<Lesson> findByCourseId(Long courseId);
}
