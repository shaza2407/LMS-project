package com.example.lms.repository;

import com.example.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Find a user by email
    User findByEmail(String email);

    // Check if a user exists by email
    boolean existsByEmail(String email);
}
