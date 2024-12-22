package com.example.lms.controller;

import com.example.lms.model.*;
import com.example.lms.service.AttendanceService;
import com.example.lms.service.CourseService;
import com.example.lms.service.EnrollmentService;
import com.example.lms.service.NotificationService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instructors/courses")
public class CourseController
{

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private NotificationService notificationService;

    Notification notification;

    // Endpoint for enrolling a student in a course
    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<String> enrollStudent(
            @PathVariable Long courseId,
            @RequestParam Long studentId)
    {
        String response = enrollmentService.enrollStudent(courseId, studentId);
        return ResponseEntity.ok(response);
    }

    // Endpoint for attending a lesson via OTP
    @PostMapping("/{courseId}/lessons/{lessonId}/attend")
    public ResponseEntity<String> attendLesson(
            @PathVariable Long courseId,
            @PathVariable Long lessonId,
            @RequestParam Long studentId,
            @RequestParam String otp)
    {
        String response = attendanceService.attendLesson(lessonId, studentId, otp);
        return ResponseEntity.ok(response);
    }

    // Endpoint for creating a new course (Admin/Instructor)
    @PostMapping("/createCourse")
    public ResponseEntity<Course> createCourse(@RequestBody Course course)
    {
        Course createdCourse = courseService.createCourse(course);
        notification = new Notification("INSTRUCTOR", createdCourse.getTitle() + " Course has been created successfully", LocalDateTime.now());
        notificationService.addNotification(notification);
        return ResponseEntity.ok(createdCourse);
    }

    // Endpoint for updating course details (Admin/Instructor)
    @PutMapping("/{courseId}/update")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long courseId,
            @RequestBody Course course)
    {
        Course updatedCourse = courseService.updateCourse(courseId, course);
        return ResponseEntity.ok(updatedCourse);
    }

    // Endpoint for deleting a course (Admin/Instructor)
    @DeleteMapping("/{courseId}/delete")
    public ResponseEntity<String> deleteCourse(@PathVariable Long courseId)
    {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok("Course deleted successfully");
    }

    // Endpoint for adding lessons to a course (Instructor)
    @PostMapping("/{courseId}/content/lessons")
    public ResponseEntity<Lesson> addLessonToCourse(
            @PathVariable Long courseId,
            @RequestBody Lesson lesson)
    {
        Lesson addedLesson = courseService.addLessonToCourse(courseId, lesson);
        return ResponseEntity.ok(addedLesson);
    }

    // Endpoint for adding assignments to a course (Instructor)
    @PostMapping("/{courseId}/content/assignments")
    public ResponseEntity<Assessment> addAssignmentToCourse(
            @PathVariable Long courseId,
            @RequestBody Assessment assignment)
    {
        Assessment addedAssignment = courseService.addAssignmentToCourse(courseId, assignment);
        return ResponseEntity.ok(addedAssignment);
    }

    // Endpoint for adding question bank to a course (Instructor)
    @PostMapping("/{courseId}/content/questionbank")
    public ResponseEntity<Question> addQuestionToCourse(
            @PathVariable Long courseId,
            @RequestBody Question questionBank)
    {
        Question addedQuestionBank = courseService.addQuestionToCourse(courseId, questionBank);
        return ResponseEntity.ok(addedQuestionBank);
    }

    // Endpoint for adding a quiz to a course (Instructor)
    @PostMapping("/{courseId}/content/quizzes")
    public ResponseEntity<Quiz> addQuizToCourse(@PathVariable Long courseId, @RequestBody Quiz quiz)
    {
        Quiz addedQuiz = courseService.addQuizToCourse(courseId, quiz);
        return ResponseEntity.ok(addedQuiz);
    }


    // Endpoint to get all courses
    @GetMapping("/getAllCourses")
    @RolesAllowed({"ADMIN", "INSTRUCTOR", "STUDENT"})
    public ResponseEntity<List<Course>> getAllCourses()
    {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    // Endpoint to get all lessons for a specific course
    @GetMapping("/{courseId}/getAllLessons")
    @RolesAllowed({"ADMIN", "INSTRUCTOR", "STUDENT"})
    public ResponseEntity<List<Lesson>> getAllLessons(@PathVariable Long courseId)
    {
        List<Lesson> lessons = courseService.getAllLessons(courseId);
        return ResponseEntity.ok(lessons);
    }

    // Endpoint to get a specific course by ID
    @GetMapping("/{courseId}/getCourse")
    @RolesAllowed({"ADMIN", "INSTRUCTOR", "STUDENT"})
    public ResponseEntity<Course> getCourseById(@PathVariable Long courseId)
    {
        Course course = courseService.getCourseById(courseId);
        return course != null ? ResponseEntity.ok(course) : ResponseEntity.notFound().build();
    }
}