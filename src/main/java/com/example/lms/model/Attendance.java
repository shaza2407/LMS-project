package com.example.lms.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Attendance {
    private Long studentId;
    private Long lessonId;
    private boolean attended;

    public Attendance(Long studentId, Long lessonId) {
        this.studentId = studentId;
        this.lessonId = lessonId;
        this.attended = false;
    }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getLessonId() { return lessonId; }
    public void setLessonId(Long lessonId) { this.lessonId = lessonId; }

    public boolean isAttended() { return attended; }
    public void markAsAttended() { this.attended = true; }
}
