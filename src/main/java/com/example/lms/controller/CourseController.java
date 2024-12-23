package com.example.lms.controller;
import com.example.lms.model.*;
import com.example.lms.service.CourseService;
import com.example.lms.service.EnrollmentService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/instructors/courses")
public class CourseController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private CourseService courseService;


    // enroll student in a course
    @RolesAllowed({"STUDENT"})
    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<String> enrollStudent(@PathVariable Long courseId, @RequestParam Long studentId) {
        String response = enrollmentService.enrollStudent(courseId, studentId);
        return ResponseEntity.ok(response);
    }


    //create new course
    @RolesAllowed({"INSTRUCTOR"})
    @PostMapping("/createCourse")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);
        return ResponseEntity.ok(createdCourse);
    }

    // update course details
    @RolesAllowed({"INSTRUCTOR","ADMIN"})
    @PutMapping("/{courseId}/update")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long courseId,
            @RequestBody Course course) {
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
        Lesson addedLesson = courseService.addLessonToCourse(courseId, lesson);
        return ResponseEntity.ok(addedLesson);
    }


    //to get the list of all courses
    @GetMapping("/getAllCourses")
    @RolesAllowed({"ADMIN", "INSTRUCTOR", "STUDENT"})
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    //to get list of all lessons
    @GetMapping("/{courseId}/getAllLessons")
    @RolesAllowed({"ADMIN", "INSTRUCTOR", "STUDENT"})
    public ResponseEntity<List<Lesson>> getAllLessons(@PathVariable Long courseId) {
        List<Lesson> lessons = courseService.getAllLessons(courseId);
        return ResponseEntity.ok(lessons);
    }

    // get course details by its id
    @GetMapping("/{courseId}/getCourse")
    @RolesAllowed({"ADMIN", "INSTRUCTOR", "STUDENT"})
    public ResponseEntity<Course> getCourseById(@PathVariable Long courseId) {
        Course course = courseService.getCourseById(courseId);
        return course != null ? ResponseEntity.ok(course) : ResponseEntity.notFound().build();
    }
}