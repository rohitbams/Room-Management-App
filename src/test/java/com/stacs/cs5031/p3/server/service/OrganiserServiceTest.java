package com.stacs.cs5031.p3.server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.stacs.cs5031.p3.server.dto.RoomDto;
import com.stacs.cs5031.p3.server.model.Organiser;
import com.stacs.cs5031.p3.server.repository.OrganiserRepository;

/**
 * This class is responsible for testing the OrganiserService class.
 * @author 190031593
 */
public class OrganiserServiceTest {

    private Organiser organiser1, organiser2; // Organiser objects for testing
    private OrganiserService organiserService; //organiser service
    private RoomService roomService; //room service

    private OrganiserRepository organiserRepository; //mocked repository

    @BeforeEach
    void setUp(){
        organiserRepository = Mockito.mock(OrganiserRepository.class);
        roomService =  Mockito.mock(RoomService.class);
        organiserService = new OrganiserService(organiserRepository, roomService);
        organiser1 = new Organiser( "James Dean", "james.dean", "password123"); 
        organiser2 = new Organiser( "Mary Dean", "mary.dean", "password123");

    }

    /**
     * Tests that an organiser can be created and saved successfully.
     */
    @Test
    void shouldCreateAnOrganiserWithoutIssue(){
        Mockito.when(organiserRepository.save(organiser1)).thenReturn(organiser1);
        assertEquals("SUCCESS!", organiserService.createOrganiser(organiser1));
    }

    /**
     * Tests that an exception is thrown when the organiser is null.
     */
    @Test
    void shouldThrowExceptionWhenOrganiserIsNull(){
        Mockito.when(organiserRepository.save(null)).thenThrow(new IllegalArgumentException("Organiser cannot be null"));


		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			organiserService.createOrganiser(null);
		}, "Expected IllegalArgumentException to be thrown, but it was not.");

        assertEquals("Organiser cannot be null", thrown.getMessage());
    }

    /**
     * Tests that an exception is thrown when the organiser name is invalid.
     */
    @Test
    void shouldThrowExceptionWhenOrganiserNameIsInvalid(){
        organiser1.setName(null);
        Mockito.when(organiserRepository.save(organiser1)).thenThrow(new IllegalArgumentException("Organiser name is invalid"));


		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			organiserService.createOrganiser(organiser1);
		}, "Expected IllegalArgumentException to be thrown, but it was not.");

        assertEquals("Organiser name is invalid", thrown.getMessage());

        organiser1.setName("");


	    thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			organiserService.createOrganiser(organiser1);
		}, "Expected IllegalArgumentException to be thrown, but it was not.");

        assertEquals("Organiser name is invalid", thrown.getMessage());
    }

    /**
     * Tests that an exception is thrown when the organiser username is invalid.
     */
    @Test
    void shouldThrowExceptionWhenOrganiserUserNameIsInvalid(){
        organiser1.setUsername(null);
        Mockito.when(organiserRepository.save(organiser1)).thenThrow(new IllegalArgumentException("Organiser username is invalid"));


		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			organiserService.createOrganiser(organiser1);
		}, "Expected IllegalArgumentException to be thrown, but it was not.");

        assertEquals("Organiser username is invalid", thrown.getMessage());

        organiser1.setUsername("");


	    thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			organiserService.createOrganiser(organiser1);
		}, "Expected IllegalArgumentException to be thrown, but it was not.");

        assertEquals("Organiser username is invalid", thrown.getMessage());
    }

    /**
     * Tests that an exception is thrown when the organiser password is invalid.
     */
    @Test
    void shouldThrowExceptionWhenOrganiserPasswordIsInvalid(){
        organiser1.setPassword(null);
        Mockito.when(organiserRepository.save(organiser1)).thenThrow(new IllegalArgumentException("Organiser password is invalid"));


		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			organiserService.createOrganiser(organiser1);
		}, "Expected IllegalArgumentException to be thrown, but it was not.");

        assertEquals("Organiser password is invalid", thrown.getMessage());

        organiser1.setPassword("");


	    thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			organiserService.createOrganiser(organiser1);
		}, "Expected IllegalArgumentException to be thrown, but it was not.");

        assertEquals("Organiser password is invalid", thrown.getMessage());
    }

    /**
     * Tests that all organisers can be retrieved successfully.
     */
    @Test
    void shouldGetAllOrganisersWithoutIssue(){
        Organiser organiser3 = Mockito.mock(Organiser.class);
        Organiser organiser4 = Mockito.mock(Organiser.class);
        ArrayList<Organiser> organisers = new ArrayList<>();

        Mockito.when(organiserRepository.findAll()).thenReturn(organisers);

        Mockito.when(organiser3.getName()).thenReturn("James Dean");
        Mockito.when(organiser3.getUsername()).thenReturn("james.dean");
        Mockito.when(organiser3.getId()).thenReturn(1);


        Mockito.when(organiser4.getName()).thenReturn("Mary Dean");
        Mockito.when(organiser4.getUsername()).thenReturn("mary.dean");
        Mockito.when(organiser4.getId()).thenReturn(2);

        assertEquals(0, organiserService.getAllOrganisers().size());

        organisers.add(organiser3);
        organisers.add(organiser4);

        assertEquals(2, organiserService.getAllOrganisers().size());
        assertEquals(organiser1.getUsername(), organiserService.getAllOrganisers().get(0).getUsername());
        assertEquals(organiser2.getUsername(), organiserService.getAllOrganisers().get(1).getUsername());
    }

    /**
     * Tests that all available rooms can be retrieved successfully.
     */
    @Test
    void shouldGetAllAvailableRoomsWithoutIssue(){
        RoomDto room1 = new RoomDto(1, "JCB 1.3", 100, true);
        RoomDto room2 = new RoomDto(2, "JCB 2.2", 300, true);

        ArrayList<RoomDto> rooms = new ArrayList<>();
        rooms.add(room1);
        rooms.add(room2);

        Mockito.when(roomService.findAvailableRooms()).thenReturn(rooms);
        assertEquals(2, organiserService.getAvailableRooms().size());
        assertEquals(room1, organiserService.getAvailableRooms().get(0));
        assertEquals(room2, organiserService.getAvailableRooms().get(1));
    }

    /**
     * Tests that all available rooms can be retrieved successfully. In this case, no rooms are available.
     */
    @Test
    void shouldGetAllAvailableRoomsIfNoneExist(){
        ArrayList<RoomDto> rooms = new ArrayList<>();
        Mockito.when(roomService.findAvailableRooms()).thenReturn(rooms);
        assertEquals(0, organiserService.getAvailableRooms().size());
    }

    @Test
    void shouldGetAllBookingsWithoutIssue(){


        
    }

    @Test
    void shouldGetBookingDetailsWithoutIssue(){

    }

    @Test
    void shouldThrowExceptionWhenBookingDetailsAreInvalid(){

    }

    @Test
    void shouldCreateBookingWithoutIssue(){

    }

    @Test
    void shouldCancelBookingWithoutIssue(){

    }

}
