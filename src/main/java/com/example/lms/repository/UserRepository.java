package com.example.lms.repository;

import com.example.lms.model.User;
import java.util.List;

public interface UserRepository {
    List<User> findAll();
    User findById(Long id);
    void save(User user);
    void deleteById(Long id);
}
