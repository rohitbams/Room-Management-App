package com.stacs.cs5031.p3.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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


}
