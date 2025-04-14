package com.stacs.cs5031.p3.server.service;

import com.stacs.cs5031.p3.server.exception.UserNotFoundException;
import com.stacs.cs5031.p3.server.model.Attendee;
import com.stacs.cs5031.p3.server.repository.AttendeeRepository;
import com.stacs.cs5031.p3.server.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
