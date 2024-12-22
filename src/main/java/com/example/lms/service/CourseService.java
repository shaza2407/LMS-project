package com.example.lms.service;

import com.example.lms.model.Course;
import com.example.lms.model.Lesson;
import com.example.lms.model.Role;
import com.example.lms.model.User;
import com.example.lms.repository.CourseRepository;
import com.example.lms.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private UserService userService; // A service to fetch the instructor (User)

    public Course addCourse(Long instructorId, Course course) {
        // Fetch the instructor from the database
        User instructor = userService.getUserById(instructorId);

        // Validate the instructor's role
        if (instructor.getRole() != Role.INSTRUCTOR) {
            throw new RuntimeException("The user is not an instructor");
        }

        // Assign the instructor to the course
        course.setInstructor(instructor);
        return courseRepository.save(course);
    }

        public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public Lesson getLessonById(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
    }
}


