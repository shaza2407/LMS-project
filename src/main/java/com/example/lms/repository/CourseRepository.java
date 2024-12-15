package com.example.lms.repository;

import com.example.lms.model.Course;
import java.util.List;

public interface CourseRepository {
    List<Course> findAll();
    Course findById(Long id);
    void save(Course course);
    void deleteById(Long id);
}
