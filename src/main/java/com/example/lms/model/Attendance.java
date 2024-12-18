package com.example.lms.model;

import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class Attendance {
    @Setter
    private Long studentId;
    @Setter
    private Long lessonId;
    private boolean attended;



    public Long getStudentId() { return studentId; }

    public Long getLessonId() { return lessonId; }

    public boolean isAttended() { return attended; }
    public void markAsAttended() { this.attended = true; }
}
