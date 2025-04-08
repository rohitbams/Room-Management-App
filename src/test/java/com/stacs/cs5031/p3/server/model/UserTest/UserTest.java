package com.stacs.cs5031.p3.server.model.UserTest;

import com.stacs.cs5031.p3.server.model.User.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest {
    private User user;

    @BeforeEach
    void setup() {
        user = new User("Alice Bob", "alicebob", "qwerty");
    }

    @Test
    void shouldCreateUser() {
        assertNotNull(user, "user should not be null");
    }

    @Test
    void shouldGetUserName() {
        assertEquals("Alice Bob", user.getName(), "User's name should be 'Alice Bob'");
    }

    @Test
    void shouldGetUsername() {
        assertEquals("johndoe", user.getUsername(), "User's username should be 'alicebob'");
    }

    @Test
    void shouldGetPassword() {
        assertEquals("password123", user.getPassword(), "User's password should be 'qwerty'");
    }



}