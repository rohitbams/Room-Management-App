package com.stacs.cs5031.p3.server.controller;

import com.stacs.cs5031.p3.server.dto.AttendeeDto;
import com.stacs.cs5031.p3.server.exception.BookingFullException;
import com.stacs.cs5031.p3.server.exception.BookingNotFoundException;
import com.stacs.cs5031.p3.server.exception.UserNotFoundException;
import com.stacs.cs5031.p3.server.mapper.AttendeeDtoMapper;
import com.stacs.cs5031.p3.server.mapper.BookingDtoMapper;
import com.stacs.cs5031.p3.server.model.Attendee;
import com.stacs.cs5031.p3.server.model.Booking;
import com.stacs.cs5031.p3.server.service.AttendeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AttendeeController class.
 * This class is a REST API controller for Attendee operations.
 */
@RestController
@RequestMapping("/api/attendees")
public class AttendeeController {

    private final AttendeeService attendeeService;

    /**
     * Constructor
     */
    @Autowired
    public AttendeeController(AttendeeService attendeeService) {
        this.attendeeService = attendeeService;
    }


    /**
     * Get all attendees.
     *
     * @return List of attendeeDtos
     */
    @GetMapping
    public ResponseEntity<List<AttendeeDto>> getAllAttendees() {
        List<Attendee> attendees = attendeeService.getAllAttendees();
        return ResponseEntity.ok(AttendeeDtoMapper.mapToDtoList(attendees));
    }


}
