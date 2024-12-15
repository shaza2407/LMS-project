package com.example.lms.service;

import com.example.lms.model.Assessment;
import com.example.lms.model.Course;
import com.example.lms.model.User;

import java.util.List;

public class PerformanceAnalyticsService {

    public String generatePerformanceReport(User student, List<Course> enrolledCourses, List<Assessment> assessments) {
        StringBuilder report = new StringBuilder();
        report.append("Performance Report for ").append(student.getName()).append(":\n\n");

        for (Course course : enrolledCourses) {
            report.append("Course: ").append(course.getTitle()).append("\n");
            List<Assessment> courseAssessments = assessments.stream()
                    .filter(a -> a.getCourseId().equals(course.getId()))
                    .toList();

            if (courseAssessments.isEmpty()) {
                report.append("- No assessments available.\n");
            } else {
                report.append("- Assessments:\n");
                for (Assessment assessment : courseAssessments) {
                    report.append("  - ").append(assessment.getTitle()).append("\n");
                }
            }
            report.append("\n");
        }
        return report.toString();
    }
}
