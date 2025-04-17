package com.stacs.cs5031.p3.server.controller;

import com.stacs.cs5031.p3.server.dto.BookingDto;
import com.stacs.cs5031.p3.server.exception.BookingNotFoundException;
import com.stacs.cs5031.p3.server.model.Booking;
import com.stacs.cs5031.p3.server.service.BookingService;
import com.stacs.cs5031.p3.server.mapper.BookingDtoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BookingController class.
 * This class is a REST API controller for Booking operations.
 */
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    /**
     * Constructor
     */
    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Get all bookings.
     *
     * @return List of BookingDto
     */
    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(BookingDtoMapper.mapToDTOList(bookings));
    }

    /**
     * Get a booking by ID.
     *
     * @param bookingId The ID of the booking
     * @return BookingDto
     */
    
    @GetMapping("/{bookingId}")
public ResponseEntity<?> getBookingById(@PathVariable Integer bookingId) {
    try {
        Booking booking = bookingService.getBookingById(Long.valueOf(bookingId))
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + bookingId));
        return ResponseEntity.ok(BookingDtoMapper.mapToDTO(booking));
    } catch (BookingNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found: " + e.getMessage());
    }
}

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(String message) {
        super(message);
    }
}

    /**
     * Create a new booking.
     *
     * @param request The booking request
     * @return Response message
     */
    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody BookingDto.BookingRequest request) {
        try {
    //        bookingService.createBooking(request, Long.valueOf(organiserID));
            return ResponseEntity.status(HttpStatus.CREATED).body("Booking created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create booking: " + e.getMessage());
        }
    }

    /**
     * Delete a booking by ID.
     *
     * @param bookingId The ID of the booking
     * @return Response message
     */
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<String> deleteBooking(@PathVariable Integer bookingId) {
        try {
            bookingService.deleteBooking(Long.valueOf(bookingId));;
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Booking deleted successfully.");
        } catch (BookingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting booking.");
        }
    }
}
