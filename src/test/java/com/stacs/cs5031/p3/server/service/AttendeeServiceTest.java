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


}
