package com.example.lms.repository;

import com.example.lms.model.Course;
import java.util.ArrayList;
import java.util.List;

public class InMemoryCourseRepository implements CourseRepository {
    private final List<Course> courses = new ArrayList<>();

    @Override
    public List<Course> findAll() {
        return courses;
    }

    @Override
    public Course findById(Long id) {
        return courses.stream().filter(course -> course.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void save(Course course) {
        courses.add(course);
    }

    @Override
    public void deleteById(Long id) {
        courses.removeIf(course -> course.getId().equals(id));
    }
}
