package com.stacs.cs5031.p3.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RoomRepositoryTest {
    @Autowired
    private RoomRepository roomRepository;
    private Room room1;
    private Room room2;
    private Room room3;

    @BeforeEach
    public void setUp() {
        room1 = new Room("Meeting Room 1", 4, true);
        room2 = new Room("Meeting Room 2", 6, false);
        room3 = new Room("Meeting Room 3", 8, true);
        roomRepository.saveAll(Arrays.asList(room1, room2, room3));
    }

    @AfterEach
    public void tearDown() {
        // Release test data after each test method
        roomRepository.deleteAll();
    }

    @Test
    void shouldSaveRoom() {
        Room room = new Room("Meeting Room", 10, false);
        roomRepository.save(room);

        List<Room> foundRooms = new ArrayList<>();
        roomRepository.findAll().forEach(foundRooms::add);
        assertTrue(foundRooms.contains(room));
    }

    @Test
    void shouldFindAllRooms() {
        List<Room> foundRooms = new ArrayList<>();
        roomRepository.findAll().forEach(foundRooms::add);
        
        assertEquals(3, foundRooms.size(), "Should have saved 3 rooms");
        assertTrue(foundRooms.contains(room1), "Should contain room1");
        assertTrue(foundRooms.contains(room2), "Should contain room2");
        assertTrue(foundRooms.contains(room3), "Should contain room3");
    }

    @Test
    void shouldFindRoomById() {
        Room foundRoom = roomRepository.findById(room1.getID()).orElse(null);
        assertNotNull(foundRoom);
        assertEquals(room1.getName(), foundRoom.getName());
        assertEquals(room1.getCapacity(), foundRoom.getCapacity());
        assertEquals(room1.isItBooked(), foundRoom.isItBooked());
    }
    
    @Test
    void shouldFindAvailableRooms() {
        List<Room> availableRooms = roomRepository.findByAvailability(true);
        assertEquals(2, availableRooms.size());
        assertFalse(availableRooms.stream().anyMatch(Room::isItBooked)); // Does not return unavailable rooms
    }
    
    @Test
    void shouldFindUnavailableRooms() {
        List<Room> unAvailableRooms = roomRepository.findByAvailability(false);
        assertEquals(1, unAvailableRooms.size());
        assertTrue(unAvailableRooms.stream().allMatch(Room::isItBooked));
    }

    @Test
    void shouldRemoveRoomById() {
        roomRepository.removeById(room1.getID());

        Iterable<Room> foundRooms = roomRepository.findAll();
        List<Room> roomList = new ArrayList<>();
        foundRooms.forEach(roomList::add);
        assertEquals(2, roomList.size(), "Should have 2 rooms saved after removing 1");
        assertFalse(roomList.contains(room1), "Should not contain room1 after removing it");
        assertTrue(roomList.contains(room2), "Should contain room2 after removing room1");
        assertTrue(roomList.contains(room3), "Should contain room3 after removing room1");
    }
}
