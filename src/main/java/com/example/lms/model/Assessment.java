package com.example.lms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long courseId;   //this assessment to which course
    private String type; // if it's quiz or assignment
    private String title; // Title of the assessment
    private String content; // Quiz questions or assignment details
    private String grade; // Grade for the assessment



}
