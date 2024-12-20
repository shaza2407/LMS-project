package com.example.lms.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role; // ADMIN, INSTRUCTOR, STUDENT
    // Constructor



    public  User() {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
//        this.role = Role.valueOf(role);
    }


}
