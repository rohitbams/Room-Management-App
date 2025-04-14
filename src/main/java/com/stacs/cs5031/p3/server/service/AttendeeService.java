package com.stacs.cs5031.p3.server.service;

import com.stacs.cs5031.p3.server.exception.BookingFullException;
import com.stacs.cs5031.p3.server.exception.BookingNotFoundException;
import com.stacs.cs5031.p3.server.exception.UserNotFoundException;
import com.stacs.cs5031.p3.server.model.Attendee;
import com.stacs.cs5031.p3.server.model.Booking;
import com.stacs.cs5031.p3.server.repository.AttendeeRepository;
import com.stacs.cs5031.p3.server.repository.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public AttendeeService(AttendeeRepository attendeeRepository, BookingRepository bookingRepository) {
        this.attendeeRepository = attendeeRepository;
        this.bookingRepository = bookingRepository;
    }

    /**
     * Gets an attendee by ID.
     *
     * @param id The attendee ID
     * @return The attendee
     * @throws UserNotFoundException if attendee is not found
     */
    public Attendee getAttendeeById(Integer id) {
        return attendeeRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public Attendee getAttendeeByUsername(String username) {
        Attendee attendee = attendeeRepository.findByUsername(username);
        if (attendee == null) {
            throw new UserNotFoundException(username);
        }
        return attendee;
    }

    // get all available attendees
    public List<Attendee> getAllAttendees() {
        return attendeeRepository.findAll();
    }

    /**
     * Get all available bookings for an attendee.
     *
     * @param attendeeId The attendee ID
     * @return List of available bookings
     */
    public List<Booking> getAvailableBookings(Integer attendeeId) {
        getAttendeeById(attendeeId);
        return attendeeRepository.findAvailableBookings(attendeeId);
    }

    /**
     * Get all unavailable bookings for an attendee.
     *
     * @param attendeeId The attendee ID
     * @return List of unavailable bookings
     */
    public List<Booking> getUnavailableBookings(Integer attendeeId) {
        getAttendeeById(attendeeId);
        return attendeeRepository.findUnavailableBookings(attendeeId);
    }

//    /**
//     * Register an attendee for a booking.
//     *
//     * @param attendeeId The attendee ID
//     * @param bookingId The booking ID
//     * @return The updated booking
//     * @throws UserNotFoundException if attendee is not found
//     * @throws BookingNotFoundException if booking is not found
//     * @throws BookingFullException if booking is already at capacity
//     */
//    @Transactional
//    public Booking registerForBooking(Integer attendeeId, Integer bookingId) {
//        Attendee attendee = getAttendeeById(attendeeId);
//        Booking booking = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new BookingNotFoundException(bookingId));
//
//        if (attendee.getRegisteredBookings().contains(booking)) {
//            throw new IllegalStateException("Attendee is already registered for this booking");
//        }
//        if (!booking.isThereSpace()) {
//            throw new BookingFullException(bookingId);
//        }
//
//        attendee.registerForBooking(booking);
//        booking.addAttendee(attendee);
//
//        attendeeRepository.save(attendee);
//        return bookingRepository.save(booking);
//    }



}
