package com.stacs.cs5031.p3.server.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Attendee model class.
 * This is a subclass of User and represents an Attendee in the system.
 * Attendees can register for bookings created by Organisers.
 */
@Entity
public class Attendee extends User {

    @ManyToMany
    @JoinTable(
            name = "attendee_bookings",
            joinColumns = @JoinColumn(name = "attendee_id"),
            inverseJoinColumns = @JoinColumn(name = "booking_id")
    )
    private List<Booking> registeredBookings = new ArrayList<>();

    protected Attendee() {
        super();
    }

    public Attendee(String name, String username, String password) {
        super(name, username, password);
    }



}