package com.stacs.cs5031.p3.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;

public class RoomTest {
    private Room room;
    
    @BeforeEach
    void setup() {
        room = new Room(3);
    }

    void shouldCreateRoom() {
        assertNotNull(room, "room should not be null");
    }
    
    void shouldGetRoomID() {
        assertNotNull(room.getID(), "room ID should not be null");
    }
    
    void shouldGetRoomName() {
        assertNotNull(room.getName(), "room name should not be null");
        assertEquals("Room " + String.valueOf(room.getID()), room.getName(), "room name should be 'Room + <ID>'");
    }

    void shouldGetRoomCapacity() {
        assertEquals(3, room.getCapacity(), "room capacity should be 3");
    }

    void shouldReturnTrue_whenRoomIsAvailable() {
        assertEquals(true, room.isAvailable(), "room should be available");
    }

    void shouldMakeAvailable() {
        room.bookRoom();
        assertEquals(false, room.isAvailable(), "room should not be available after booking");
        room.makeAvailable();
        assertEquals(true, room.isAvailable(), "room should be available after being made available");
    }
    
    void shouldBookRoom() {
        assertEquals(true, room.isAvailable(), "room should be available before booking");
        room.bookRoom();
        assertEquals(false, room.isAvailable(), "room should not be available after booking");
    }
    
    void shouldReturnFalse_whenRoomIsUnavailable() {
        room.bookRoom();
        assertEquals(false, room.isAvailable(), "room should not be available after booking");
    }
}
