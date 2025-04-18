package com.stacs.cs5031.p3.server.controller;

import com.stacs.cs5031.p3.server.dto.AttendeeDto;
import com.stacs.cs5031.p3.server.exception.BookingFullException;
import com.stacs.cs5031.p3.server.exception.BookingNotFoundException;
import com.stacs.cs5031.p3.server.exception.UserNotFoundException;
import com.stacs.cs5031.p3.server.model.Attendee;
import com.stacs.cs5031.p3.server.model.Booking;
import com.stacs.cs5031.p3.server.model.Organiser;
import com.stacs.cs5031.p3.server.model.Room;
import com.stacs.cs5031.p3.server.service.AttendeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class AttendeeControllerTest {

    @Mock
    private AttendeeService attendeeService;

    @InjectMocks
    private AttendeeController attendeeController;

    private Attendee attendee;
    private Booking booking;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        attendee = new Attendee("Ringo Star", "drummerboy", "peaceandlove");
        Room room = new Room("Cavern Club", 200);
        Organiser organiser = new Organiser("Brian Epstein", "manager", "lennonsucks");
        setId(organiser, 1);
        booking = new Booking("Beatles concert", room, new Date(), 60, organiser);
        setId(booking, 1);
    }

    @Test
    void getAllAttendees_ShouldReturnAttendees() {
        List<Attendee> attendees = Arrays.asList(attendee);
        when(attendeeService.getAllAttendees()).thenReturn(attendees);
        ResponseEntity<List<AttendeeDto>> response = attendeeController.getAllAttendees();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getAttendeeById_ShouldReturnAttendee_WhenExists() {
        when(attendeeService.getAttendeeById(1)).thenReturn(attendee);
        ResponseEntity<AttendeeDto> response = attendeeController.getAttendeeById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getAttendeeById_ShouldReturnNotFound_WhenNotExists() {
        when(attendeeService.getAttendeeById(1)).thenThrow(new UserNotFoundException(1));
        ResponseEntity<AttendeeDto> response = attendeeController.getAttendeeById(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAvailableBookings_ShouldReturnBookings() {
        List<Booking> bookings = Arrays.asList(booking);
        when(attendeeService.getAvailableBookings(1)).thenReturn(bookings);
        ResponseEntity<?> response = attendeeController.getAvailableBookings(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getUnavailableBookings_ShouldReturnBookings() {
        List<Booking> bookings = Arrays.asList(booking);
        when(attendeeService.getUnavailableBookings(1)).thenReturn(bookings);
        ResponseEntity<?> response = attendeeController.getUnavailableBookings(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getRegisteredBookings_ShouldReturnBookings() {
        List<Booking> bookings = Arrays.asList(booking);
        when(attendeeService.getRegisteredBookings(1)).thenReturn(bookings);
        ResponseEntity<?> response = attendeeController.getRegisteredBookings(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void registerForBooking_ShouldReturnBooking_WhenSuccess() {
        when(attendeeService.registerForBooking(1, 1)).thenReturn(booking);
        ResponseEntity<?> response = attendeeController.registerForBooking(1, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void registerForBooking_ShouldReturnBadRequest_WhenBookingFull() {
        when(attendeeService.registerForBooking(1, 1)).thenThrow(new BookingFullException(1));
        ResponseEntity<?> response = attendeeController.registerForBooking(1, 1);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deregisterFromBooking_ShouldReturnBooking_WhenSuccess() {
        when(attendeeService.deregisterFromBooking(1, 1)).thenReturn(booking);
        ResponseEntity<?> response = attendeeController.deregisterFromBooking(1, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void cancelRegistration_ShouldReturnNotFound_WhenBookingNotFound() {
        when(attendeeService.deregisterFromBooking(1, 1)).thenThrow(new BookingNotFoundException(1));
        ResponseEntity<?> response = attendeeController.deregisterFromBooking(1, 1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private void setId(Object object, int id) {
        try {
            Field field = object.getClass().getSuperclass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(object, id);
        } catch (Exception e) {
        }
    }

}
