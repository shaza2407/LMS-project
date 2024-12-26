package com.example.lms.service;

import com.example.lms.model.User;
import com.example.lms.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<?> register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        user = userRepository.save(newUser);
        String token = jwtService.generateToken(user);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<?> login(User user) {
        User foundUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (foundUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!bCryptPasswordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String token = jwtService.generateToken(foundUser);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", foundUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);  // Return null if not found
}

    public User findByUsername(String username) {
        return userRepository.findByName(username).orElse(null);
    }


}




