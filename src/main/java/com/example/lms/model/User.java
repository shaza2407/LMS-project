package com.example.lms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class User {
    @Id
   
    private Long id;
    private String name;
    private String email;
    private String role; // "Admin", "Instructor", "Student"

//    // Constructor
//    public User() {}

    //    public User(Long id, String name, String email, String role) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.role = role;
//    }

    // Getters and Setters
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//
//    public String getEmail() { return email; }
//    public void setEmail(String email) { this.email = email; }
//
//    public String getRole() { return role; }
//    public void setRole(String role) { this.role = role; }
}
