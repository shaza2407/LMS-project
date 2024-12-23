package com.example.lms.repository;

import com.example.lms.model.Attendance;
import com.example.lms.model.Lesson;
import com.example.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    // Check if a student has already attended a lesson
    boolean existsByLessonAndStudent(Lesson lesson, User student);

    // Find attendance records for a specific lesson
    List<Attendance> findByLesson(Lesson lesson);

    // Find attendance records for a student
    List<Attendance> findByStudent(User student);

    List<Attendance> findByLessonCourseId(Long courseId);

}

