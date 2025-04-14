package com.stacs.cs5031.p3.server.repository;

import com.stacs.cs5031.p3.server.model.Attendee;
import com.stacs.cs5031.p3.server.model.Booking;
import com.stacs.cs5031.p3.server.model.Organiser;
import com.stacs.cs5031.p3.server.model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class AttendeeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AttendeeRepository attendeeRepository;

    private Attendee attendee;
    private Room room;
    private Organiser organiser;
    private Booking availableBooking;
    private Booking registeredBooking;
    private Booking fullBooking;

    @BeforeEach
    public void setup() {
        attendee = new Attendee("Hannah Montana", "hannahmontana", "mileystewart");
        entityManager.persist(attendee);

        room = new Room("Room 101", 5);
        entityManager.persist(room);

        organiser = new Organiser("Jackson Stewart", "jackson", "jacksonstewart123");
        entityManager.persist(organiser);

        availableBooking = new Booking("Hannah Montana concert", room, new Date(), 60, organiser);
        entityManager.persist(availableBooking);

        registeredBooking = new Booking("Hannah Montana concert", room, new Date(), 60, organiser);
        entityManager.persist(registeredBooking);
        attendee.getRegisteredBookings().add(registeredBooking);
        entityManager.persist(attendee);

        fullBooking = new Booking("Full Event", room, new Date(), 60, organiser);
        fillBookingToCapacity(fullBooking);
        entityManager.persist(fullBooking);

        entityManager.flush();
    }

    private void fillBookingToCapacity(Booking booking) {
        for (int i = 0; i < booking.getRoom().getCapacity(); i++) {
            Attendee fakeAttendee = new Attendee("Fake" + i, "fake" + i, "password");
            entityManager.persist(fakeAttendee);
            booking.getAttendees().add(fakeAttendee);
        }
    }

    @Test
    public void testFindByUsername() {
        Attendee found = attendeeRepository.findByUsername("hannahmontana");
        assertNotNull(found);
        assertEquals("hannahmontana", found.getUsername());
    }




}
