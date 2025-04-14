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
import java.util.List;

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
    private String eventName = "Hannah Montana concert";

    @BeforeEach
    public void setup() {
        attendee = new Attendee("Hannah Montana", "hannahmontana", "mileystewart");
        entityManager.persist(attendee);

        room = new Room("Room 101", 5);
        entityManager.persist(room);

        organiser = new Organiser("Jackson Stewart", "jackson", "jacksonstewart123");
        entityManager.persist(organiser);

        availableBooking = new Booking(eventName, room, new Date(), 60, organiser);
        entityManager.persist(availableBooking);

        registeredBooking = new Booking(eventName, room, new Date(), 60, organiser);
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

    @Test
    public void shouldFindRegisteredBookings() {
        List<Booking> registeredBookings = attendeeRepository.findRegisteredBookings(attendee.getId());
        assertNotNull(registeredBookings);
        assertEquals(1, registeredBookings.size());
        assertEquals("Hannah Montana concert", registeredBookings.get(0).getName());
    }

    @Test
    public void shouldFindAvailableBookings() {
        List<Booking> availableBookings = attendeeRepository.findAvailableBookings(attendee.getId());
        assertNotNull(availableBookings);
        assertEquals(1, availableBookings.size());
        assertEquals("Hannah Montana concert", availableBookings.get(0).getName());
    }


}
