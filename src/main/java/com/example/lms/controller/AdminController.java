//package com.example.lms.controller;
//
//import com.example.lms.model.User;
//import com.example.lms.service.AdminService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@AllArgsConstructor
//@RequestMapping("/api/users/admins")
//public class AdminController {
//
//     private final AdminService adminService;
//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers() {
//        List<User> users = AdminService.getAllUsers();
//        return ResponseEntity.ok(users);
//    }
//    // GET a user by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable String id) {
//        User user = AdminService.getUserById(id);
//        return ResponseEntity.ok(user);
//    }
//
//    // POST to create a new user
//    @PostMapping
//    public ResponseEntity<User> createUser(@RequestBody User user) {
//        User createdUser = AdminService.createUser(user);
//        return ResponseEntity.ok(createdUser);
//    }
//
//    // DELETE a user by ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
//        AdminService.deleteUser(id);
//        return ResponseEntity.noContent().build();
//    }
//
//
//}
