package com.example.lms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Course {
    private Long id;
    private String title;
    private String description;

    // Constructor
//    public Course(Long id, String title, String description) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//    }

    // Getters and Setters
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public String getTitle() { return title; }
//    public void setTitle(String title) { this.title = title; }
//
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
}
