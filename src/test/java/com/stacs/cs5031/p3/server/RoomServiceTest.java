package com.stacs.cs5031.p3.server;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

// Coordinate booking a room (checking availability, updating database)
// Handle complex search criteria for rooms
// Manage room capacity validation
// Coordinate between multiple repositories if needed

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;
    private Room testRoom;
    private RoomDTO testRoomDTO;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setup() {
        testRoom = new Room(20);

        /*
        * In production, these fields are set by:
        * - id: JPA's @GeneratedValue strategy
        * - name: @PostPersist callback
        * Using ReflectionTestUtils to simulate this behaviour for testing
        */
        ReflectionTestUtils.setField(testRoom, "id", 1);
        ReflectionTestUtils.setField(testRoom, "name", "Test Room");
        
        // Expected DTO after mapping
        testRoomDTO = new RoomDTO(1, "Test Room", 20, true);

        // stub repository behaviour
        // when(roomRepository.save(testRoom)).thenReturn(testRoom);
        // when room entity exists
        // when(roomRepository.findById(1)).thenReturn(Optional.of(testRoom));
    }

    @Test
    void shouldCreateRoom() {
        when(roomRepository.save(any(Room.class))).thenReturn(testRoom);
        RoomDTO result = roomService.createRoom(testRoom.getCapacity());

        assertNotNull(result);
        assertEquals(testRoomDTO.getId(), result.getId());
        assertEquals(testRoomDTO.getName(), result.getName());
        assertEquals(testRoomDTO.getCapacity(), result.getCapacity());
        assertEquals(testRoomDTO.isAvailable(), result.isAvailable());

        verify(roomRepository).save(any(Room.class));
    }
    
    @Test
    void shouldThrowException_whenRoomCapacityIsZero() {
        assertThrows(IllegalArgumentException.class, 
            () -> roomService.createRoom(0),
            "Should throw exception for room with zero capacity");
    }

    @Test
    void shouldThrowException_whenRoomCapacityIsNegative() {
        assertThrows(IllegalArgumentException.class, 
            () -> roomService.createRoom(-1),
            "Should throw exception for room with negative capacity");
    }

    @Test
    void shouldFindRoomById_whenRoomExists() {
        // testRoom is initialised in setUp(), will not return empty
        when(roomRepository.findById(1)).thenReturn(Optional.of(testRoom));
        RoomDTO result = roomService.findRoomById(1);
        
        assertNotNull(result, "Result should not be null");
        assertEquals(testRoomDTO.getId(), result.getId(), "ID should match");
        assertEquals(testRoomDTO.getName(), result.getName(), "Name should match");
        assertEquals(testRoomDTO.getCapacity(), result.getCapacity(), "Capacity should match");
        assertTrue(result.isAvailable(), "Room should be available");
    }
    
    @Test
    void shouldThrowRoomNotFoundException_whenRoomDoesNotExist() {
        when(roomRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(RoomNotFoundException.class, () -> roomService.findRoomById(999));
        verify(roomRepository).findById(999);
    }

    @Test
    void shouldFindAllRooms() {
        when(roomRepository.findAll()).thenReturn(List.of(testRoom));
        List<RoomDTO> result = roomService.findAllRooms();
        
        assertNotNull(result, "Result list should not be null");
        assertFalse(result.isEmpty(), "Result list should not be empty");
    
        RoomDTO resultDto = result.get(0);

        assertEquals(1, result.size());
        assertEquals(testRoomDTO.getId(), resultDto.getId());
        assertEquals(testRoomDTO.getName(), resultDto.getName());
        assertEquals(testRoomDTO.getCapacity(), resultDto.getCapacity());
        assertEquals(testRoomDTO.isAvailable(), resultDto.isAvailable());
        verify(roomRepository).findAll();
    }
    
    @Test
    void shouldFindAvailableRooms() {
        when(roomRepository.findByAvailability(true)).thenReturn(List.of(testRoom));
        List<RoomDTO> result = roomService.findAvailableRooms();

        assertNotNull(result, "Result list should not be null");
        assertFalse(result.isEmpty(), "Result list should not be empty");
    
        RoomDTO resultDto = result.get(0);

        assertEquals(1, result.size());
        assertEquals(testRoomDTO.getId(), resultDto.getId());
        assertEquals(testRoomDTO.getName(), resultDto.getName());
        assertEquals(testRoomDTO.getCapacity(), resultDto.getCapacity());
        assertEquals(testRoomDTO.isAvailable(), resultDto.isAvailable());
        verify(roomRepository).findByAvailability(true);
    }
    
    @Test
    void shouldBookRoom() {
        when(roomRepository.findById(1)).thenReturn(Optional.of(testRoom));
        RoomDTO result = roomService.bookRoom(testRoom);
        
        assertNotNull(result, "Result should not be null");
        assertEquals(testRoomDTO.getId(), result.getId(), "ID should match");
        assertEquals(testRoomDTO.getName(), result.getName(), "Name should match");
        assertEquals(testRoomDTO.getCapacity(), result.getCapacity(), "Capacity should match");
        assertFalse(result.isAvailable(), "Room should be unavailable after booking");
        verify(roomRepository).findById(1);
    }
    
    @Test
    void shouldThrowException_whenBookingUnavailableRoom() {
        Room unavailableRoom = new Room(10);
        ReflectionTestUtils.setField(unavailableRoom, "id", 2);
        unavailableRoom.bookRoom(); // Set availability to false
        when(roomRepository.findById(2)).thenReturn(Optional.of(unavailableRoom));

        assertThrows(RoomNotAvailableException.class, () -> roomService.bookRoom(unavailableRoom));
        verify(roomRepository).findById(2);
    }

    @Test
    void shouldMakeRoomAvailable() {
        Room unavailableRoom = new Room(10); // Available initially
        ReflectionTestUtils.setField(unavailableRoom, "id", 2);
        ReflectionTestUtils.setField(unavailableRoom, "name", "Unavailable Room");
        unavailableRoom.bookRoom(); // Set availability to false
        when(roomRepository.findById(2)).thenReturn(Optional.of(unavailableRoom));
        
        RoomDTO result = roomService.makeRoomAvailable(unavailableRoom);
        
        assertNotNull(result, "Result should not be null");
        assertEquals(unavailableRoom.getID(), result.getId(), "ID should match");
        assertEquals(unavailableRoom.getName(), result.getName(), "Name should match");
        assertEquals(unavailableRoom.getCapacity(), result.getCapacity(), "Capacity should match");
        assertTrue(result.isAvailable(), "Room should be available after being made available");
    }
}
