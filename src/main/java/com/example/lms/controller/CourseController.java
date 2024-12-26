package com.example.lms.controller;
import com.example.lms.model.*;
import com.example.lms.repository.UserRepository;
import com.example.lms.service.CourseService;
import com.example.lms.service.EnrollmentService;
import com.example.lms.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructors/courses")
public class CourseController {


    @Autowired
    private CourseService courseService;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    //create new course
    @RolesAllowed({"INSTRUCTOR"})
    @PostMapping("/createCourse")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User instructor = userService.findById(Long.valueOf(name));
        if (instructor == null || instructor.getRole() != Role.INSTRUCTOR) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        course.setInstructor(instructor);
        Course createdCourse = courseService.createCourse(course);

        return ResponseEntity.ok(createdCourse);
    }


    // update course details
    @RolesAllowed({"INSTRUCTOR","ADMIN"})
    @PutMapping("/{courseId}/update")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long courseId,
            @RequestBody Course course) {

        String currentUserIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
        Long currentUserId = Long.valueOf(currentUserIdStr);
        Course existingCourse = courseService.getCourseById(courseId);
        if (existingCourse == null || !existingCourse.getInstructor().getId().equals(currentUserId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        Course updatedCourse = courseService.updateCourse(courseId, course);
        return ResponseEntity.ok(updatedCourse);
    }

    //deleting a course
    @RolesAllowed({"INSTRUCTOR","ADMIN"})
    @DeleteMapping("/{courseId}/delete")
    public ResponseEntity<String> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok("Course deleted successfully");
    }

    //adding lesson to course
    @RolesAllowed({"INSTRUCTOR"})
    @PostMapping("/{courseId}/content/lessons")
    public ResponseEntity<Lesson> addLessonToCourse(
            @PathVariable Long courseId,
            @RequestBody Lesson lesson) {
        String currentUserIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
        Long currentUserId = Long.valueOf(currentUserIdStr);
        Course course = courseService.getCourseById(courseId);
        if (course == null || !course.getInstructor().getId().equals(currentUserId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        Lesson addedLesson = courseService.addLessonToCourse(courseId, lesson);
        return ResponseEntity.ok(addedLesson);
    }


    //to get the list of all courses
    @GetMapping("/getAllCourses")
    @RolesAllowed({"ADMIN", "INSTRUCTOR"})
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    //to get list of all lessons
    @GetMapping("/{courseId}/getAllLessons")
    @RolesAllowed({"ADMIN", "INSTRUCTOR"})
    public ResponseEntity<List<Lesson>> getAllLessons(@PathVariable Long courseId) {
        List<Lesson> lessons = courseService.getAllLessons(courseId);
        return ResponseEntity.ok(lessons);
    }

    // get course details by its id
    @GetMapping("/{courseId}/getCourse")
    @RolesAllowed({"ADMIN", "INSTRUCTOR"})
    public ResponseEntity<Course> getCourseById(@PathVariable Long courseId) {
        Course course = courseService.getCourseById(courseId);
        return course != null ? ResponseEntity.ok(course) : ResponseEntity.notFound().build();
    }

    //get all enrolled students to specific course
    @RolesAllowed({"ADMIN", "INSTRUCTOR"})
    @GetMapping("/{courseId}/enrolledStudents")
    public ResponseEntity<List<User>> getEnrolledStudents(@PathVariable Long courseId) {
        String instructorIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
        Long instructorId = Long.valueOf(instructorIdStr);
        Course course = courseService.getCourseById(courseId);
        if (course == null || !course.getInstructor().getId().equals(instructorId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        List<User> enrolledStudents = enrollmentService.getEnrolledStudents(courseId);
        if (enrolledStudents.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok(enrolledStudents);
    }

}