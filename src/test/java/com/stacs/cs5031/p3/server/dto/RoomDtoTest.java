package com.stacs.cs5031.p3.server.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.stacs.cs5031.p3.server.dto.RoomDto;

public class RoomDtoTest {
    private RoomDto roomDTO;

    @BeforeEach
    void setUp() {
        roomDTO = new RoomDto(1, "Room 1", 10, true);
    }

    @Test
    void shouldCreateRoomDTO() {
        assertEquals(1, roomDTO.getId(), "Should have correct ID");
        assertEquals("Room 1", roomDTO.getName(), "Should have correct name");
        assertEquals(10, roomDTO.getCapacity(), "Should have correct capacity");
        assertTrue(roomDTO.isAvailable(), "Should be available");
    }

    @Test
    void shouldGetId() {
        assertEquals(1, roomDTO.getId());
    }

    @Test
    void shouldGetName() {
        assertEquals("Room 1", roomDTO.getName());
    }

    @Test
    void shouldGetCapacity() {
        assertEquals(10, roomDTO.getCapacity());
    }

    @Test
    void shouldCheckAvailability() {
        RoomDto availableRoom = new RoomDto(1, "Room 1", 10, true);
        RoomDto unavailableRoom = new RoomDto(2, "Room 2", 15, false);

        assertTrue(availableRoom.isAvailable(), "Room should be available");
        assertFalse(unavailableRoom.isAvailable(), "Room should be unavailable");
    }
}
