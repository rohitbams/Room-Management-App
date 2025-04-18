package com.stacs.cs5031.p3.server.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.stacs.cs5031.p3.server.model.Room;
import com.stacs.cs5031.p3.server.repository.RoomRepository;

@DataJpaTest
public class RoomRepositoryTest {
   @Autowired
   private RoomRepository roomRepository;
   private Room room1;
   private Room room2;
   private Room room3;

   @BeforeEach
   public void setUp() {
       room1 = new Room("Room 1", 4);
       room2 = new Room("Room 2", 6);
       room3 = new Room("Room 3", 8);
       roomRepository.saveAll(Arrays.asList(room1, room2, room3));
   }

   @AfterEach
   public void tearDown() {
       // Release test data after each test method
       roomRepository.deleteAll();
   }

   @Test
   void shouldSaveAndFindRooms() {
       // room1, room2, room3 are already saved in setUp()
       List<Room> foundRooms = StreamSupport.stream(roomRepository.findAll().spliterator(), false)
       .collect(Collectors.toList());

       assertTrue(foundRooms.contains(room1), "room1 should be saved");
       assertTrue(foundRooms.contains(room2), "room2 should be saved");
       assertTrue(foundRooms.contains(room3), "room3 should be saved");
   }

   @Test
   void shouldReturnCorrectRoomCount_whenOneRoomIsSaved() {
       roomRepository.deleteAll(); // clear repository
       roomRepository.save(new Room("New Room", 3));
       long roomCount = StreamSupport.stream(roomRepository.findAll().spliterator(), false).count();
       assertEquals(1, roomCount);
   }

   @Test
   void shouldReturnCorrectRoomCount_whenMultipleRoomsAreSaved() {
       // three rooms are saved in setUp()
       long roomCount = StreamSupport.stream(roomRepository.findAll().spliterator(), false).count();
       assertEquals(3, roomCount);

       roomRepository.deleteAll(); // Clear up repository
       // save two rooms
       roomRepository.save(new Room("Room 4", 5));
       roomRepository.save(new Room("Room 5", 5));
       roomCount = StreamSupport.stream(roomRepository.findAll().spliterator(), false).count();
       assertEquals(2, roomCount);
   }

   @Test
   void shouldFindRoomById() {
       Room foundRoom = roomRepository.findById(room1.getID()).orElse(null);
       assertNotNull(foundRoom);
       assertEquals(room1.getName(), foundRoom.getName());
       assertEquals(room1.getCapacity(), foundRoom.getCapacity());
       assertEquals(room1.isAvailable(), foundRoom.isAvailable());
   }

   @Test
   void shouldFindAvailableRooms() {
       List<Room> availableRooms = roomRepository.findByAvailability(true);

       // all rooms saved in setup() are available by default
       assertTrue(availableRooms.contains(room1));
       assertTrue(availableRooms.contains(room2));
       assertTrue(availableRooms.contains(room3));
       assertEquals(3, availableRooms.size());
   }

   @Test
   void shouldFindUnavailableRooms() {
       List<Room> unAvailableRooms = roomRepository.findByAvailability(false);

       // all rooms saved in setup() are available
       assertEquals(0, unAvailableRooms.size());
   }

   @Test
   void shouldRemoveRoomById() {
       roomRepository.deleteById(room1.getID());

       List<Room> foundRooms = StreamSupport.stream(roomRepository.findAll().spliterator(), false)
               .collect(Collectors.toList());
       assertEquals(2, foundRooms.size(), "Should have 2 rooms saved after removing 1");
       assertFalse(foundRooms.contains(room1), "Should not contain room1 after removing it");
       assertTrue(foundRooms.contains(room2), "Should contain room2 after removing room1");
       assertTrue(foundRooms.contains(room3), "Should contain room3 after removing room1");
   }

   @Test
   void shouldUpdateAvailability_whenRoomIsBooked() {
       Room savedRoom = roomRepository.findById(room1.getID()).orElse(null);
       assertTrue(savedRoom.isAvailable(), "Room should be available initially");

       savedRoom.bookRoom(); // Set availability to false
       roomRepository.save(savedRoom);

       Room updatedRoom = roomRepository.findById(savedRoom.getID()).orElse(null);
       assertNotNull(updatedRoom, "Room should still exist after updating availability");
       assertFalse(updatedRoom.isAvailable(), "Room should be unavailable after booking");
   }

   @Test
   void shouldUpdateAvailability_whenRoomIsMadeAvailable() {
       Room savedRoom = roomRepository.findById(room1.getID()).orElse(null);
       assertNotNull(savedRoom);
       savedRoom.bookRoom(); // Set availability to false
       roomRepository.save(savedRoom);
       assertFalse(savedRoom.isAvailable());

       savedRoom.makeAvailable(); // Set availability to true
       roomRepository.save(savedRoom);

       Room updatedRoom = roomRepository.findById(savedRoom.getID()).orElse(null);
       assertNotNull(updatedRoom, "Room should still exist after updating availability");
       assertTrue(updatedRoom.isAvailable(), "Room should be available after update");
   }
}
