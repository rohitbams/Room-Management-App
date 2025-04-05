package com.stacs.cs5031.p3.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RoomRepositoryTest {
    @Autowired
    private RoomRepository roomRepository;
    private Room testRoom;

    @BeforeEach
    public void setUp() {
        // Initialize test data before each test method
        testRoom = new Room("rehearsal room", 5);
        roomRepository.save(testRoom);
    }

    @AfterEach
    public void tearDown() {
        // Release test data after each test method
        roomRepository.delete(testRoom);
    }

    // Tests the insertion operation
    @Test
    void shouldFindSavedRoomById() {
        Room savedRoom = roomRepository.findById(testRoom.getID()).orElse(null);
        assertNotNull(savedRoom);
        assertEquals(testRoom.getName(), savedRoom.getName());
        assertEquals(testRoom.getCapacity(), savedRoom.getCapacity());
        assertEquals(testRoom.isItBooked(), savedRoom.isItBooked());
    }
}
