package com.example.lms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Assessment {
    private Long id;
    private String type; // "Quiz" or "Assignment"
    private String title;
    private String description;
    private Long courseId;



//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public String getType() { return type; }
//    public void setType(String type) { this.type = type; }
//
//    public String getTitle() { return title; }
//    public void setTitle(String title) { this.title = title; }
//
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//
//    public Long getCourseId() { return courseId; }
//    public void setCourseId(Long courseId) { this.courseId = courseId; }
}
