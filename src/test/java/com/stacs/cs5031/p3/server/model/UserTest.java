package com.stacs.cs5031.p3.server.model;

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
        assertEquals("alicebob", user.getUsername(), "User's username should be 'alicebob'");
    }

    @Test
    void shouldGetPassword() {
        assertEquals("qwerty", user.getPassword(), "User's password should be 'qwerty'");
    }

    @Test
    void shouldSetUsername() {
        user.setUsername("newusername");
        assertEquals("newusername", user.getUsername(), "username should be updated to 'newusername'");
    }

    @Test
    void shouldSetName() {
        user.setName("Jane Doe");
        assertEquals("Jane Doe", user.getName(), "name should be updated to 'Jane Doe'");
    }

    @Test
    void shouldSetPassword() {
        user.setPassword("newpassword");
        assertEquals("newpassword", user.getPassword(), "password should be updated to 'newpassword'");
    }

}