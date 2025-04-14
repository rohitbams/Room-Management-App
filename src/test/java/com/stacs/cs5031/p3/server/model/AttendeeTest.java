package com.stacs.cs5031.p3.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class AttendeeTest {

    private Attendee attendee;
    private Booking booking;
    private Date startTime;

    @BeforeEach
    void setup() {
        attendee = new Attendee("John Doe", "johndoe", "password123");

        // mock booking
        Room room = new Room("name ", 1);
        Organiser organiser = new Organiser("organiser_name", "organiser", "password");
        startTime = new Date();
        booking = new Booking("event_name", room, startTime,60, organiser);
    }

    @Test
    void shouldCreateAttendee() {
        assertNotNull(attendee, "attendee should not be null");
        assertEquals("John Doe", attendee.getName(), "name should match");
        assertEquals("johndoe", attendee.getUsername(), "username should match");
        assertEquals("password123", attendee.getPassword(), "password should match");
    }

    @Test
    void shouldHaveEmptyBookingsListByDefault() {
        assertTrue(attendee.getRegisteredBookings().isEmpty(), "registered bookings should be empty initially");
    }

    @Test
    void shouldRegisterForBooking() {
        attendee.registerForBooking(booking);
        assertEquals(1, attendee.getRegisteredBookings().size(), "should have one registered booking");
        assertTrue(attendee.getRegisteredBookings().contains(booking), "should contain the registered booking");
    }

    @Test
    void shouldDeregisterFromBooking() {
        attendee.registerForBooking(booking);
        assertEquals(1, attendee.getRegisteredBookings().size(), "should have one registered booking initially");
        attendee.deRegisterFromBooking(booking);
        assertTrue(attendee.getRegisteredBookings().isEmpty(), "should have no registered bookings after deregistering");
    }

    @Test
    void shouldNotRegisterForSameBookingTwice() {
        attendee.registerForBooking(booking);
        attendee.registerForBooking(booking); // Register again
        assertEquals(1, attendee.getRegisteredBookings().size(), "should have only one booking registration");
    }
}
