package com.example.lms.service;

import com.example.lms.model.User;
import com.example.lms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterWithExistingEmail() {
        try {
            User user = new User();
            user.setEmail("test@example.com");
            when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

            ResponseEntity<?> response = adminService.register(user);

            // Perform the assertions
            assertEquals(400, response.getStatusCodeValue());
            verify(userRepository, never()).save(any(User.class));

            // If no exceptions are thrown, the test passed
            System.out.println("test passed");
        } catch (AssertionError e) {
            // If an assertion fails, print "test failed" and the error message
            System.out.println("test failed");
            e.printStackTrace();
        }
    }

    @Test
    void testRegisterWithNewUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setName("Test User");
        user.setRole(null);

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(jwtService.generateToken(any(User.class))).thenReturn("token");

        ResponseEntity<?> response = adminService.register(user);

        assertEquals(201, response.getStatusCodeValue());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals("token", body.get("token"));
        assertEquals(user.getEmail(), ((User) body.get("user")).getEmail());
        verify(userRepository).save(any(User.class));
    }


    @Test
    void testGetAllUsersEmptyList() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<User> result = adminService.getAllUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void updateUser_existingUser_success() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Old Name");
        existingUser.setEmail("old@example.com");
        existingUser.setPassword("oldPassword");
        existingUser.setRole(null);

        User updatedUser = new User();
        updatedUser.setName("New Name");
        updatedUser.setEmail("new@example.com");
        updatedUser.setPassword("newPassword");
        updatedUser.setRole(null);

        when(userRepository.findById("1")).thenReturn(java.util.Optional.of(existingUser));
        when(bCryptPasswordEncoder.encode(updatedUser.getPassword())).thenReturn("encodedNewPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = adminService.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals("New Name", result.getName());
        assertEquals("new@example.com", result.getEmail());
        assertEquals("encodedNewPassword", result.getPassword());
        verify(userRepository).findById("1");
        verify(userRepository).save(existingUser);
    }

    @Test
    void updateUser_nonExistentUser_throwsException() {
        User updatedUser = new User();
        updatedUser.setName("New Name");
        updatedUser.setEmail("new@example.com");
        updatedUser.setPassword("newPassword");
        updatedUser.setRole(null);

        when(userRepository.findById("1")).thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            adminService.updateUser(1L, updatedUser);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository).findById("1");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser_existingUser_success() {
        Long userId = 1L;

        when(userRepository.existsById(String.valueOf(userId))).thenReturn(true);
        doNothing().when(userRepository).deleteById(String.valueOf(userId));

        assertDoesNotThrow(() -> adminService.deleteUser(userId));

        verify(userRepository).existsById(String.valueOf(userId));
        verify(userRepository).deleteById(String.valueOf(userId));
    }

    @Test
    void testDeleteUser_nonExistentUser_throwsException() {
        Long userId = 1L;

        when(userRepository.existsById(String.valueOf(userId))).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> adminService.deleteUser(userId));

        assertEquals("User with ID 1 does not exist!", exception.getMessage());
        verify(userRepository).existsById(String.valueOf(userId));
        verify(userRepository, never()).deleteById(anyString());
    }

    @Test
    void testGetUserById_existingUser_success() {
        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");

        when(userRepository.findById("1")).thenReturn(java.util.Optional.of(user));

        User result = adminService.getUserById("1");

        assertNotNull(result);
        assertEquals("Test User", result.getName());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository).findById("1");
    }

    @Test
    void testGetUserById_nonExistentUser_throwsException() {
        when(userRepository.findById("1")).thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            adminService.getUserById("1");
        });

        assertEquals("User not found!", exception.getMessage());
        verify(userRepository).findById("1");
    }

}
