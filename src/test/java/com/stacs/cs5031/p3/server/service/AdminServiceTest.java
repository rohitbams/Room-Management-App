package com.stacs.cs5031.p3.server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.stacs.cs5031.p3.server.dto.RoomDto;
import com.stacs.cs5031.p3.server.exception.RoomNotFoundException;
import com.stacs.cs5031.p3.server.model.Attendee;
import com.stacs.cs5031.p3.server.model.Organiser;
import com.stacs.cs5031.p3.server.model.Room;

public class AdminServiceTest {
    @InjectMocks
    private AdminService adminService;

    @Mock
    private RoomService roomService;

    @Mock
    private OrganiserService organiserService;

    @Mock
    private AttendeeService attendeeService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllRooms() {
        List<RoomDto> allRooms = List.of(
            new RoomDto(1, "Room 1", 10, true),
            new RoomDto(2, "Room 2", 10, true),
            new RoomDto(3, "Room 3", 10, true)
        );

        when(roomService.findAllRooms()).thenReturn(allRooms);
        ArrayList<RoomDto> result = adminService.getAllRooms();
        assertEquals(allRooms.size(), result.size());

        // Verify the contents of the returned list
        for (int i = 0; i < allRooms.size(); i++) {
            RoomDto expected = allRooms.get(i);
            RoomDto actual = result.get(i);

            assertEquals(expected.getName(), actual.getName(), "Room name should match");
            assertEquals(expected.getCapacity(), actual.getCapacity(), "Room capacity should match");
            assertEquals(expected.isAvailable(), actual.isAvailable(), "Room availability should match");
        }

        // Verify that roomService.findAllRooms() was called exactly once
        verify(roomService, times(1)).findAllRooms();
    }

    @Test
    void shouldGetAttendees() {
        ArrayList<Attendee> attendees = new ArrayList<>();
        attendees.add(new Attendee("Ada", "ada", "12345"));
        attendees.add(new Attendee("G Yeung", "g.yeung", "12345"));
        attendees.add(new Attendee("Toothless", "toothless", "12345"));

        when(attendeeService.getAllAttendees()).thenReturn(attendees);
        ArrayList<Attendee> result = adminService.getAttendees();
        assertEquals(attendees.size(), result.size());

        // Verify the contents of the returned list
        for (int i = 0; i < attendees.size(); i++) {
            Attendee expected = attendees.get(i);
            Attendee actual = result.get(i);

            assertEquals(expected.getName(), actual.getName(), "Attendee name should match");
            assertEquals(expected.getUsername(), actual.getUsername(), "Attendee username should match");
            assertEquals(expected.getPassword(), actual.getPassword(), "Attendee password should match");
        }

        // Verify that roomService.findAllRooms() was called exactly once
        verify(attendeeService, times(1)).getAllAttendees();

    }

    @Test
    void shouldGetOrganisers() {
        ArrayList<Organiser> organisers = new ArrayList<>();
        organisers.add(new Organiser("John Doe", "john.doe", "12345"));
        organisers.add(new Organiser("Lily Wong", "lily.wong", "12345"));
        organisers.add(new Organiser("Toothless", "toothless", "12345"));

        when(organiserService.getAllOrganisers()).thenReturn(organisers);
        ArrayList<Organiser> result = adminService.getOrganisers();
        assertEquals(organisers.size(), result.size());

        // Verify the contents of the returned list
        for (int i = 0; i < organisers.size(); i++) {
            Organiser expected = organisers.get(i);
            Organiser actual = result.get(i);

            assertEquals(expected.getName(), actual.getName(), "Organiser name should match");
            assertEquals(expected.getUsername(), actual.getUsername(), "Organiser username should match");
            assertEquals(expected.getPassword(), actual.getPassword(), "Organiser password should match");
        }

        // Verify that roomService.findAllRooms() was called exactly once
        verify(organiserService, times(1)).getAllOrganisers();
    }

    @Test
    void shouldReturnTrue_whenAddRoomWithCapacityHigherThanZero() {
        Room testRoom = new Room("Test Room", 10);
        when(roomService.createRoom(testRoom.getName(), testRoom.getCapacity())).thenReturn(new RoomDto(1, "Test Room", 10, true));
        assertEquals(true, adminService.addRoom(testRoom));
    }

    @Test
    void shouldReturnFalse_whenAddRoomWithZeroCapacity() {
        Room testRoom = new Room("Test Room", 0);
        when(roomService.createRoom(testRoom.getName(), testRoom.getCapacity()))
                .thenThrow(IllegalArgumentException.class);
        assertEquals(false, adminService.addRoom(testRoom));
    }

    @Test
    void shouldReturnFalse_whenAddRoomWithNegativeCapacity() {
        Room testRoom = new Room("Test Room", -1);
        when(roomService.createRoom(testRoom.getName(), testRoom.getCapacity())).thenThrow(IllegalArgumentException.class);
        assertEquals(false, adminService.addRoom(testRoom));
    }

    @Test
    void shouldReturnTrue_whenRemoveRoomWithValidRoomId() {
        int validRoomId = 1;
        doNothing().when(roomService).deleteRoomById(validRoomId);
        assertEquals(true, adminService.removeRoom(validRoomId));
    }

    @Test
    void shouldReturnFalse_whenRemoveRoomWithInvalidRoomId() {
        int inValidRoomId = 999;
        doThrow(RoomNotFoundException.class).when(roomService).deleteRoomById(inValidRoomId);
        assertEquals(false, adminService.removeRoom(inValidRoomId));
    }
}
