package com.stacs.cs5031.p3.server.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class RoomTest {
    private Room room;

    @BeforeEach
    void setup() {
        room = new Room("Room 1", 3);

        // Simulate the database persistence behavior
        ReflectionTestUtils.setField(room, "id", 1);
    }

    @Test
    void shouldCreateRoom() {
        assertNotNull(room, "room should not be null");
    }

    @Test
    void shouldGetRoomID() {
        assertEquals(1, room.getID(), "room ID should be 1");
    }

    @Test
    void shouldGetRoomName() {
        assertEquals("Room 1", room.getName(), "room name should be 'Room 1'");
    }

    @Test
    void shouldBeAvailableByDefault() {
        assertTrue(room.isAvailable(), "room should be available by default");
    }

    @Test
    void shouldGetRoomCapacity() {
        assertEquals(3, room.getCapacity(), "room capacity should be 3");
    }

    @Test
    void shouldToggleAvailability() {
        assertTrue(room.isAvailable(), "room should be available initially");

        room.bookRoom();
        assertFalse(room.isAvailable(), "room should not be available after booking");

        room.makeAvailable();
        assertTrue(room.isAvailable(), "room should be available after being made available");
    }
}
