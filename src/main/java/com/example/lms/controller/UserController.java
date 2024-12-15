package com.example.lms.controller;

import com.example.lms.model.User;
import com.example.lms.service.UserService;
import java.util.List;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public User getUserById(Long id) {
        return userService.getUserById(id);
    }

    public void createUser(User user) {
        userService.createUser(user);
    }

    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }
}
