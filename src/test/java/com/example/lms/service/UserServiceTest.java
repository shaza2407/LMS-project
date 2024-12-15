package com.example.lms.service;

import com.example.lms.model.User;
import com.example.lms.repository.InMemoryUserRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    @Test
    public void testCreateAndRetrieveUser() {
        InMemoryUserRepository repository = new InMemoryUserRepository();
        UserService service = new UserService(repository);

        User user = new User(1L, "Aisha", "Aisha@gmail.com", "Student");
        service.createUser(user);

        List<User> users = service.getAllUsers();
        assertEquals(1, users.size());
        assertEquals("Aisha", users.get(0).getName());
    }

    @Test
    public void testDeleteUser() {
        InMemoryUserRepository repository = new InMemoryUserRepository();
        UserService service = new UserService(repository);

        User user = new User(1L, "Aisha", "Aisha@gmail.com", "Student");
        service.createUser(user);

        service.deleteUser(1L);
        assertTrue(service.getAllUsers().isEmpty());
    }
}
