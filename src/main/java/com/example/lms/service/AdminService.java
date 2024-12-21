//package com.example.lms.service;
//
//import com.example.lms.model.User;
//import com.example.lms.repository.UserRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@AllArgsConstructor
//@Service
//public class AdminService {
//
//    private  final UserRepository userRepository;
//
//    // Fetch all users
//    public  List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    // Fetch user by ID
//    public  User getUserById(String id) {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found!"));
//    }
//
//    // Create a new user
//    public  User createUser(User user) {
//        return userRepository.save(user);
//    }
//
//    // Delete a user by ID
//    public  void deleteUser(String id) {
//        // Check if the user exists before attempting to delete
//        if (!userRepository.existsById(id)) {
//            throw new RuntimeException("User with ID " + id + " does not exist!");
//        }
//        userRepository.deleteById(id);
//    }
//}
