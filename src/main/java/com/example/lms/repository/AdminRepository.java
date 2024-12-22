package com.example.lms.repository;

import com.example.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<User, String> {
    // Find a user by email
    Optional<User> findByEmail(String email);//make it optional just in case there is a null

    // Check if a user exists by email
    Boolean existsByEmail(String email);

    //get all users
    List<User> findAll();
}
