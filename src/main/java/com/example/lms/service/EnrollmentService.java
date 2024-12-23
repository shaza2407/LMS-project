package com.example.lms.service;

import com.example.lms.model.Course;
import com.example.lms.model.Enrollment;
import com.example.lms.model.Notification;
import com.example.lms.model.User;
import com.example.lms.repository.CourseRepository;
import com.example.lms.repository.EnrollmentRepository;
import com.example.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EnrollmentService
{
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;


    public String enrollStudent(Long courseId, Long studentId)
    {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        User student = userRepository.findById(String.valueOf(studentId))
                .orElseThrow(() -> new RuntimeException("Student not found"));
        if (enrollmentRepository.existsByCourseAndStudent(course, student))
        {
            return "Student is already enrolled in this course";
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollment.setEnrolledOn(LocalDate.now());
        enrollmentRepository.save(enrollment);
        User Instructor = course.getInstructor();
        if (Instructor != null) {
            String message = "Student " + student.getName() + " has enrolled in your course: " + course.getTitle();
            Notification notification = new Notification(
                    message,
                    LocalDate.now().atStartOfDay(), // Timestamp
                    Instructor // Sender
            );
            notificationService.addNotification(notification); // Save notification

            String body = "Student " + student.getName() + " has enrolled in your course: " + course.getTitle();
            notificationService.setNotificationsEmail(Instructor.getEmail(), "Course enrollment", body);
        }



        User STUDENT = course.getInstructor();
        if (STUDENT != null) {
            String message = "Enrolled in " + course.getTitle() + " successfully" + course.getTitle();
            Notification notification = new Notification(
                    message,
                    LocalDate.now().atStartOfDay(), // Timestamp
                    STUDENT // Sender
            );
            notificationService.addNotification(notification); // Save notification
        }

        String body = "Enrolled in " + course.getTitle() + " successfully" + course.getTitle();
        notificationService.setNotificationsEmail(STUDENT.getEmail(), "Course enrollment", body);

        return "Student successfully enrolled in the course";
    }
}
//- Students (enrollment confirmation, grades).
//        - Instructors (students enrolling in their course).