package com.stacs.cs5031.p3.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;

public class RoomTest {
    private Room room;
    
    @BeforeEach
    void setup() {
        room = new Room("meeting_room", 3, true);
    }

    void shouldCreateRoom() {
        assertNotNull(room, "room should not be null");
    }

    void shouldGetRoomID() {
        assertEquals(3, room.getID(), "room ID should be 3");
    }

    void shouldGetRoomName() {
        assertEquals("meeting_room", room.getName(), "room name should be 'meeting_room'");
    }

    void shouldGetRoomCapacity() {
        assertEquals(3, room.getCapacity(), "room capacity should be 3");
    }

    void shouldReturnFalse_whenRoomIsNotBooked() {
        assertEquals(false, room.isItBooked(), "room should not be booked");
    }

    void shouldReturnTrue_whenRoomIsBooked() {
        room = new Room("meeting_room", 3, false); // set availability to false
        assertEquals(false, room.isItBooked(), "room should not be booked");
    }
}
