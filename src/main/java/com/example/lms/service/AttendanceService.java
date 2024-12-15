package com.example.lms.service;

import com.example.lms.model.Attendance;

import java.util.ArrayList;
import java.util.List;

public class AttendanceService {
    private final List<Attendance> attendanceList = new ArrayList<>();

    public void recordAttendance(Long studentId, Long lessonId) {
        Attendance attendance = new Attendance(studentId, lessonId);
        attendance.markAsAttended();
        attendanceList.add(attendance);
    }

    public List<Attendance> getAttendanceByStudent(Long studentId) {
        List<Attendance> studentAttendance = new ArrayList<>();
        for (Attendance attendance : attendanceList) {
            if (attendance.getStudentId().equals(studentId)) {
                studentAttendance.add(attendance);
            }
        }
        return studentAttendance;
    }
}
