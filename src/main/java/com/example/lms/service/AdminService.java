package com.example.lms.service;

import com.example.lms.model.User;
import com.example.lms.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // Create a new user
    public ResponseEntity<?> register(User user) {
        if (adminRepository.existsByEmail(user.getEmail())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        user = adminRepository.save(newUser);
        String token = jwtService.generateToken(user);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Fetch all users
    public List<User> getAllUsers() {
        return adminRepository.findAll();
    }
    
    //update user
    public User updateUser(Long id, User updatedUser) {
        User user = adminRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
        user.setRole(updatedUser.getRole());
        return adminRepository.save(user);
    }

    // DELETE a user by ID
    public void deleteUser(Long id) {
        // Check if the user exists before attempting to delete
        if (!adminRepository.existsById(String.valueOf(id))) {
            throw new RuntimeException("User with ID " + id + " does not exist!");
        }else{
        }
        adminRepository.deleteById(String.valueOf(id));
    }
    // Fetch user by ID
    public  User getUserById(String id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

}

