package com.stacs.cs5031.p3.server.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class AdminTest {
    private Admin admin;

    @BeforeEach
    void setup() {
        // pass mock objects into the Admin instance
        admin = new Admin("Test Admin", "test.admin", "12345");
    }

    @Test
    void shouldCreateAdmin() {
        assertNotNull(admin, "admin should not be null");
    }

    @Test
    void shouldGetId() {
        // Simulate database persistence behaviour
        ReflectionTestUtils.setField(admin, "id", 1);
        assertEquals(1, admin.getId());
    }

    @Test
    void shouldGetName() {
        assertEquals("Test Admin", admin.getName());
    
    }
    
    @Test
    void shouldGetUsername() {
        assertEquals("test.admin", admin.getUsername());
    }

    @Test
    void shouldGetPassword() {
        assertEquals("12345", admin.getPassword());
    }
}
