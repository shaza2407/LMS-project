package com.example.lms.controller;

import com.example.lms.model.Course;
import com.example.lms.service.CourseService;
import java.util.List;

public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    public Course getCourseById(Long id) {
        return courseService.getCourseById(id);
    }

    public void createCourse(Course course) {
        courseService.createCourse(course);
    }

    public void deleteCourse(Long id) {
        courseService.deleteCourse(id);
    }
}
