package com.stacs.cs5031.p3.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// Coordinate booking a room (checking availability, updating database)
// Handle complex search criteria for rooms
// Manage room capacity validation
// Coordinate between multiple repositories if needed

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;
    private Room room1;
    private Room room2;
    private Room room3;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setup() {
        room1 = new Room("Meeting Room1", 10, false);
        room2 = new Room("Meeting Room2", 3, true);
        room3 = new Room("Meeting Room3", 8, false);
    }

    @Test
    void shouldCreateRoom() {
        when(roomRepository.save(any(Room.class))).thenReturn(room1).thenReturn(room2);
        Room createdRoom1 = roomService.createRoom(room1);
        assertNotNull(createdRoom1);
        assertEquals(room1.getID(), createdRoom1.getID());
        assertEquals(room1.getName(), createdRoom1.getName());
        assertEquals(room1.getCapacity(), createdRoom1.getCapacity());

        Room createdRoom2 = roomService.createRoom(room2);
        assertNotNull(createdRoom2);
        assertEquals(room1.getID(), createdRoom2.getID());
        assertEquals(room1.getName(), createdRoom2.getName());
        assertEquals(room1.getCapacity(), createdRoom2.getCapacity());
    }

    @Test
    void shouldGetRoomName() {
        when(roomRepository.findById(room1.getID())).thenReturn(Optional.of(room1)).thenReturn(Optional.of(room2)).thenReturn(Optional.of(room3)); //TODO what if NullPointerException is thrown
        assertEquals("Meeting Room1", roomService.getName(room1));
        assertEquals("Meeting Room2", roomService.getName(room2));
        assertEquals("Meeting Room3", roomService.getName(room3));
    }
    
    @Test
    void shouldGetRoomId() {
        assertEquals(room1.getID(), roomService.getID(room1));
        assertEquals(room2.getID(), roomService.getID(room2));
        assertEquals(room3.getID(), roomService.getID(room3));
    }
    
    @Test
    void shouldGetRoomCapacity() {
        when(roomRepository.findById(room1.getID())).thenReturn(Optional.of(room1)).thenReturn(Optional.of(room2)).thenReturn(Optional.of(room3));
        assertEquals(10, roomService.getCapacity(room1));
        assertEquals(3, roomService.getCapacity(room2));
        assertEquals(8, roomService.getCapacity(room3));
    }
    
    @Test
    void shouldReturnFalse_whenRoomIsNotBooked() {
        when(roomRepository.findById(room2.getID())).thenReturn(Optional.of(room2));
        assertEquals(false, roomService.getCapacity(room2));
    }
    
    @Test
    void shouldReturnTrue_whenRoomIsBooked() {
        when(roomRepository.findById(room1.getID())).thenReturn(Optional.of(room1)).thenReturn(Optional.of(room3));
        assertEquals(false, roomService.getCapacity(room1));
        assertEquals(false, roomService.getCapacity(room3));
    }
}
