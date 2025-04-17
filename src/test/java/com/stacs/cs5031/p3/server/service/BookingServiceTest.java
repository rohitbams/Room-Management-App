//package com.stacs.cs5031.p3.server.service.impl;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.stacs.cs5031.p3.server.model.*;
//import com.stacs.cs5031.p3.server.model.Booking;
//import com.stacs.cs5031.p3.server.model.Organiser;
//import com.stacs.cs5031.p3.server.model.Room;
//import com.stacs.cs5031.p3.server.dto.BookingDto;
//import com.stacs.cs5031.p3.server.exception.BookingConflictException;
//import com.stacs.cs5031.p3.server.exception.EntityNotFoundException;
//import com.stacs.cs5031.p3.server.exception.ResourceUnavailableException;
//import com.stacs.cs5031.p3.server.repository.BookingRepository;
//import com.stacs.cs5031.p3.server.service.BookingService;
//import com.stacs.cs5031.p3.server.service.RoomService;
//import com.stacs.cs5031.p3.server.service.UserService;
//
///**
// * Implementation of the BookingService interface.
// */
//@Service
//public class BookingServiceImpl implements BookingService {
//
//    private final BookingRepository bookingRepository;
//    private final RoomService roomService;
//    private final UserService userService;
//
//    @Autowired
//    public BookingServiceImpl(BookingRepository bookingRepository, RoomService roomService, UserService userService) {
//        this.bookingRepository = bookingRepository;
//        this.roomService = roomService;
//        this.userService = userService;
//    }
//
//    @Override
//    public List<Booking> getAllBookings() {
//        return (List<Booking>) bookingRepository.findAll();
//    }
//
//    @Override
//    public Optional<Booking> getBookingById(Long id) {
//        return bookingRepository.findById(id);
//    }
//
//    @Override
//    @Transactional
//    public Booking saveBooking(Booking booking) {
//        return bookingRepository.save(booking);
//    }
//
//    @Override
//    @Transactional
//    public void deleteBooking(Long id) {
//        bookingRepository.deleteById(id);
//    }
//
//    @Override
//    @Transactional
//    public Booking registerAttendee(Long bookingId, Long attendeeId) {
//        // Get booking
//        Booking booking = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new EntityNotFoundException("Booking not found with ID: " + bookingId));
//
//        // Get attendee
//        Attendee attendee = userService.getAttendeeById(attendeeId)
//                .orElseThrow(() -> new EntityNotFoundException("Attendee not found with ID: " + attendeeId));
//
//        // Check if there's space in the room
//        if (!booking.isThereSpace()) {
//            throw new ResourceUnavailableException("Room is at capacity");
//        }
//
//        // Register attendee
//        if (!booking.addAttendee(attendee)) {
//            throw new ResourceUnavailableException("Failed to add attendee to booking");
//        }
//
//        // Save and return updated booking
//        return bookingRepository.save(booking);
//    }
//
//    @Override
//    @Transactional
//    public Booking unregisterAttendee(Long bookingId, Long attendeeId) {
//        // Get booking
//        Booking booking = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new EntityNotFoundException("Booking not found with ID: " + bookingId));
//
//        // Get attendee
//        Attendee attendee = userService.getAttendeeById(attendeeId)
//                .orElseThrow(() -> new EntityNotFoundException("Attendee not found with ID: " + attendeeId));
//
//        // Unregister attendee
//        if (!booking.removeAttendee(attendee)) {
//            throw new EntityNotFoundException("Attendee not registered for this booking");
//        }
//
//        // Save and return updated booking
//        return bookingRepository.save(booking);
//    }
//
//    @Override
//    public List<Booking> getBookingsByRoom(Long roomId) {
//        return bookingRepository.findByRoomId(roomId);
//    }
//
//    @Override
//    public List<Booking> getBookingsByOrganiser(Long organiserId) {
//        return bookingRepository.findByOrganiserId(organiserId);
//    }
//
//    @Override
//    public List<Booking> getBookingsByAttendee(Long attendeeId) {
//        return bookingRepository.findByAttendeeId(attendeeId);
//    }
//
//    @Override
//    public boolean hasConflict(Long roomId, Date startTime, long duration) {
//        // Calculate end time
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(startTime);
//        calendar.add(Calendar.MINUTE, (int) duration);
//        Date endTime = calendar.getTime();
//
//        // Check for conflicts
//        List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(roomId, startTime, endTime);
//        return !conflictingBookings.isEmpty();
//    }
//
//    @Override
//    @Transactional
//    public Booking createBooking(BookingDto bookingDTO, Long organiserId) {
//        // Get room
//        Room room = roomService.getRoomById(bookingDTO.getRoomId())
//                .orElseThrow(() -> new EntityNotFoundException("Room not found with ID: " + bookingDTO.getRoomId()));
//
//        // Get organiser
//        Organiser organiser = userService.getOrganiserById(organiserId)
//                .orElseThrow(() -> new EntityNotFoundException("Organiser not found with ID: " + organiserId));
//
//        // Check if room is available
//        if (!room.isAvailable()) {
//            throw new ResourceUnavailableException("Room is not available");
//        }
//
//        // Check for booking conflicts
//        if (hasConflict(bookingDTO.getRoomId(), bookingDTO.getStartTime(), bookingDTO.getDuration())) {
//            throw new BookingConflictException("Booking conflicts with an existing booking");
//        }
//
//        // Create new booking
//        Booking booking = new Booking(
//                bookingDTO.getEventName(),
//                room,
//                bookingDTO.getStartTime(),
//                bookingDTO.getDuration(),
//                organiser
//        );
//
//        // Save and return booking
//        return bookingRepository.save(booking);
//    }
//}