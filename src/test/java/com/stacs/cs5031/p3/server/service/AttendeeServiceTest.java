package com.stacs.cs5031.p3.server.service;

import com.stacs.cs5031.p3.server.exception.UserNotFoundException;
import com.stacs.cs5031.p3.server.model.Attendee;
import com.stacs.cs5031.p3.server.model.Booking;
import com.stacs.cs5031.p3.server.model.Organiser;
import com.stacs.cs5031.p3.server.model.Room;
import com.stacs.cs5031.p3.server.repository.AttendeeRepository;
import com.stacs.cs5031.p3.server.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AttendeeServiceTest {
    @Mock
    private AttendeeRepository attendeeRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private AttendeeService attendeeService;

    private Attendee attendee;
    private Booking booking;
    private Date startTime;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        attendee = new Attendee("Harry Potter", "theboywholived", "july31");
        Room room = new Room("Great Hall ", 400);
        Organiser organiser = new Organiser("Albus Dumbledore", "headmaster", "ihatestudents");
        startTime = new Date();
        booking = new Booking("event_name", room, startTime,60, organiser);
    }

    @Test
    void getAttendeeById_ShouldReturnAttendee_WhenAttendeeExists() {
        when(attendeeRepository.findById(1)).thenReturn(Optional.of(attendee));
        Attendee result = attendeeService.getAttendeeById(1);
        assertNotNull(result);
        assertEquals(attendee, result);
        verify(attendeeRepository).findById(1);
    }

    @Test
    void getAttendeeById_ShouldThrowException_WhenAttendeeDoesNotExist() {
        when(attendeeRepository.findById(10)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> attendeeService.getAttendeeById(10));
        verify(attendeeRepository).findById(10);
    }

    @Test
    void getAttendeeByUsername_shouldReturnAttendee_whenAttendeeExists() {
        when(attendeeRepository.findByUsername("theboywholived")).thenReturn(attendee);
        Attendee result = attendeeService.getAttendeeByUsername("theboywholived");
        assertEquals(attendee, result);
        verify(attendeeRepository).findByUsername("theboywholived");
    }

    @Test
    void getAttendeeByUsername_shouldThrowUserNotFoundException_whenAttendeeDoesNotExist() {
        when(attendeeRepository.findByUsername("nonexistent")).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> {
            attendeeService.getAttendeeByUsername("nonexistent");
        });
        verify(attendeeRepository).findByUsername("nonexistent");
    }

    @Test
    void getAvailableBookings_ShouldReturnBookings() {
        when(attendeeRepository.findById(1)).thenReturn(Optional.of(attendee));
        when(attendeeRepository.findAvailableBookings(1)).thenReturn(Arrays.asList(booking));
        List<Booking> result = attendeeService.getAvailableBookings(1);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(booking, result.get(0));
        verify(attendeeRepository).findById(1);
        verify(attendeeRepository).findAvailableBookings(1);
    }




//    @Test
//    void registerForBooking_ShouldRegisterAttendee_WhenBookingHasSpace() {
//        when(attendeeRepository.findById(1)).thenReturn(Optional.of(attendee));
//        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));
//        when(attendeeRepository.save(any(Attendee.class))).thenReturn(attendee);
//        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
//        booking.getRoom().getCapacity();
//        Booking result = attendeeService.registerForBooking(1, 1);
//        assertNotNull(result);
//        verify(attendeeRepository).findById(1);
//        verify(bookingRepository).findById(1);
//        verify(attendeeRepository).save(attendee);
//        verify(bookingRepository).save(booking);
//    }


}
