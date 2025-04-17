package com.stacs.cs5031.p3.server.service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stacs.cs5031.p3.server.dto.BookingDto;
import com.stacs.cs5031.p3.server.dto.RoomDto;
import com.stacs.cs5031.p3.server.exception.EntityNotFoundException;
import com.stacs.cs5031.p3.server.exception.ResourceUnavailableException;
import com.stacs.cs5031.p3.server.exception.RoomNotFoundException;
import com.stacs.cs5031.p3.server.model.Attendee;
import com.stacs.cs5031.p3.server.model.Booking;
import com.stacs.cs5031.p3.server.model.Room;
import com.stacs.cs5031.p3.server.repository.BookingRepository;
import com.stacs.cs5031.p3.server.repository.RoomRepository;

@Service
public class BookingServiceImpl implements BookingService {

   private final BookingRepository bookingRepository;
   private final RoomService roomService;
//    private final UserService userService;
   
   @Autowired
    private AttendeeService attendeeService;

    @Autowired
    private RoomRepository roomRepository;



    public BookingServiceImpl(
        BookingRepository bookingRepository,
        RoomService roomService,
        // UserService userService,
        AttendeeService attendeeService,
        RoomRepository roomRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.roomService = roomService;
        // this.userService = userService;
        this.attendeeService = attendeeService;
        this.roomRepository = roomRepository;
    }
    

   @Override
   public List<Booking> getAllBookings() {
       return bookingRepository.findAll();
   }

   @Override
   public Optional<Booking> getBookingById(Long id) {
       // Convert Long to int for compatibility with your model
       return bookingRepository.findById(id.intValue());
   }

   @Override
   @Transactional
   public Booking saveBooking(Booking booking) {
       return bookingRepository.save(booking);
   }

   @Override
   @Transactional
   public void deleteBooking(Long id) {
       // Convert Long to int for compatibility with your model
       bookingRepository.deleteById(id.intValue());
   }

   @Override
   @Transactional
   public Booking registerAttendee(Long bookingId, Long attendeeId) {
       // Get booking - convert Long to int
       Booking booking = bookingRepository.findById(bookingId.intValue())
               .orElseThrow(() -> new EntityNotFoundException("Booking not found with ID: " + bookingId));
   
       // Get attendee - with null check
       Attendee attendee = attendeeService.getAttendeeById(attendeeId.intValue());
       if (attendee == null) {
           throw new EntityNotFoundException("Attendee not found with ID: " + attendeeId);
       }
   
       // Check if there's space in the room
       if (!booking.isThereSpace()) {
           throw new ResourceUnavailableException("Room is at capacity");
       }
   
       // Register attendee
       if (!booking.addAttendee(attendee)) {
           throw new ResourceUnavailableException("Failed to add attendee to booking");
       }
   
       // Save and return updated booking
       return bookingRepository.save(booking);
   }
   

   @Override
   @Transactional
   public Booking unregisterAttendee(Long bookingId, Long attendeeId) {
       // Get booking - convert Long to int
       Booking booking = bookingRepository.findById(bookingId.intValue())
               .orElseThrow(() -> new EntityNotFoundException("Booking not found with ID: " + bookingId));
   
       // Get attendee - with null check
       Attendee attendee = attendeeService.getAttendeeById(attendeeId.intValue());
       if (attendee == null) {
           throw new EntityNotFoundException("Attendee not found with ID: " + attendeeId);
       }
   
       // Unregister attendee
       if (!booking.removeAttendee(attendee)) {
           throw new EntityNotFoundException("Attendee not registered for this booking");
       }
   
       // Save and return updated booking
       return bookingRepository.save(booking);
   }
   

   @Override
   public List<Booking> getBookingsByRoom(Long roomId) {
       // Convert Long to int for compatibility with your model
       return bookingRepository.findByRoomId(roomId.intValue());
   }

   @Override
   public List<Booking> getBookingsByOrganiser(Long organiserId) {
       // Convert Long to int for compatibility with your model
       return bookingRepository.findByOrganiserId(organiserId.intValue());
   }

   @Override
   public List<Booking> getBookingsByAttendee(Long attendeeId) {
       // Convert Long to int for compatibility with your model
       return bookingRepository.findByAttendeeId(attendeeId.intValue());
   }

   @Override
   public boolean hasConflict(Long roomId, Date startTime, long duration) {
       // Calculate end time
       Calendar calendar = Calendar.getInstance();
       calendar.setTime(startTime);
       calendar.add(Calendar.MINUTE, (int) duration);
       Date endTime = calendar.getTime();

       // Check for conflicts - convert Long to int
       List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(roomId.intValue(), startTime, endTime);
       return !conflictingBookings.isEmpty();
   }

   public Booking createBooking(Integer attendeeId, RoomDto roomDto) {
    // Get attendee - with null check
    Attendee attendee = attendeeService.getAttendeeById(attendeeId);
    if (attendee == null) {
        throw new EntityNotFoundException("Attendee not found with ID: " + attendeeId);
    }

    // Get room from repository
    Room room = roomRepository.findById(roomDto.getId())
            .orElseThrow(() -> new RoomNotFoundException(roomDto.getId()));

    // Create and save booking
    Booking booking = new Booking();
    booking.setAttendee(attendee);
    booking.setRoom(room);
    booking.setBookingTime(LocalDateTime.now());

    return bookingRepository.save(booking);
}


//    @Override
//    @Transactional
//    public Booking createBooking(BookingDto.BookingRequest bookingDTO, Long organiserId) {
//        // Get room - convert Long to int
//        Room room = roomService.getRoomById(bookingDTO.getRoomId().intValue())
//                .orElseThrow(() -> new EntityNotFoundException("Room not found with ID: " + bookingDTO.getRoomId()));

//        // Get organiser - convert Long to int
//        Organiser organiser = userService.getOrganiserById(organiserId.intValue())
//                .orElseThrow(() -> new EntityNotFoundException("Organiser not found with ID: " + organiserId));

//        // Check if room is available
//        if (!room.isAvailable()) {
//            throw new ResourceUnavailableException("Room is not available");
//        }

//        // Check for booking conflicts
//        if (hasConflict(bookingDTO.getRoomId(), bookingDTO.getStartTime(), bookingDTO.getDuration())) {
//            throw new BookingConflictException("Booking conflicts with an existing booking");
//        }

//        // Create new booking
//        Booking booking = new Booking(
//                bookingDTO.getEventName(),
//                room,
//                bookingDTO.getStartTime(),
//                bookingDTO.getDuration(),
//                organiser
//        );

//        // Save booking
//        Booking savedBooking = bookingRepository.save(booking);

//        return savedBooking;
//    }

    @Override
    public Booking createBooking(BookingDto.BookingRequest bookingDTO, Long organiserId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}