package com.example.lms.service;

import com.example.lms.model.User;
import com.example.lms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "Aisha", "aisha@gmail.com", "Admin");
    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        // Act
        var users = userService.getAllUsers();

        // Assert
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("Aisha", users.get(0).getName());
        verify(userRepository, times(1)).findAll(); // Verify that findAll was called once
    }

    @Test
    public void testGetUserById() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(user);

        // Act
        var foundUser = userService.getUserById(1L);

        // Assert
        assertNotNull(foundUser);
        assertEquals("Aisha", foundUser.getName());
        verify(userRepository, times(1)).findById(1L); // Verify that findById was called once
    }

    @Test
    public void testCreateUser() {
        // Arrange
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        var createdUser = userService.createUser(user);

        // Assert
        assertNotNull(createdUser);
        assertEquals("Aisha", createdUser.getName());
        verify(userRepository, times(1)).save(any(User.class)); // Verify that save was called once
    }

    @Test
    public void testDeleteUser() {
        // Arrange
        doNothing().when(userRepository).deleteById(1L);

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository, times(1)).deleteById(1L); // Verify that deleteById was called once
    }

    @Test
    public void testGetUserById_NotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(1L);
        });
        assertEquals("User not found!", exception.getMessage());
    }
}
