package com.stacs.cs5031.p3.server.service;

import java.util.ArrayList;
import java.util.List;

import com.stacs.cs5031.p3.server.model.Booking;
import org.springframework.stereotype.Service;

import com.stacs.cs5031.p3.server.dto.BookingDto;
import com.stacs.cs5031.p3.server.dto.OrganiserDto;
import com.stacs.cs5031.p3.server.dto.RoomDto;
import com.stacs.cs5031.p3.server.model.Room;
import com.stacs.cs5031.p3.server.model.Organiser;
import com.stacs.cs5031.p3.server.repository.OrganiserRepository;

/**
 * This class is the service layer for the Organiser entity.
 * @author 190031593
 */
@Service
public class OrganiserService {

    private OrganiserRepository organiserRepository;
    private RoomService roomService;
    private BookingService bookingService;

    /**
     * Constructor for the OrganiserService class.
     * @param organiserRepository - organiser repository.
     * @param roomService - room service.
     * @param bookingService - booking service.
     */
    public OrganiserService(OrganiserRepository organiserRepository, RoomService roomService, BookingService bookingService) {
        this.organiserRepository = organiserRepository;
        this.roomService = roomService;
        this.bookingService = bookingService;
    }

    /**
     * This method is used to create an organiser and save it to the database.
     * @param organiser - The organiser to be created.
     * @return String - The status of the operation.
     * @throws IllegalArgumentException - If the organiser credentials are invalid.
     */
    public String createOrganiser(Organiser organiser) {
        if (organiser == null) {
            throw new IllegalArgumentException("Organiser cannot be null");
        }

        String name = organiser.getName();
        String username = organiser.getUsername();
        String password = organiser.getPassword(); 

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Organiser name is invalid");
        }
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Organiser username is invalid");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Organiser password is invalid");
        }

        organiserRepository.save(organiser);
        return "SUCCESS!";
    }

    /***
     * This method is used to get all the organisers from the database.
     * @return ArrayList<OrganiserDto> - List of all the organisers.
     */
    public ArrayList<OrganiserDto> getAllOrganisers() {
        ArrayList<Organiser> organisers = new ArrayList<Organiser>(organiserRepository.findAll());
        ArrayList<OrganiserDto> organiserDtos = new ArrayList<OrganiserDto>();
        organisers.forEach(organiser -> {
            OrganiserDto organiserDto = new OrganiserDto(organiser.getId(), organiser.getName(), organiser.getUsername());
            organiserDtos.add(organiserDto);
        });

        return organiserDtos;
    }
    

    /**
     * This method is used to get all the available rooms from the database.
     * @return ArrayList<RoomDto> - List of all the available rooms.
     */
    public List<RoomDto> getAvailableRooms() {
        return roomService.findAvailableRooms();
    }

     public ArrayList<String> getMyBookings() {
         return new ArrayList<String>();
     }

     public ArrayList<Booking> getMyBooking(int bookingId) {
         return new ArrayList<Booking>();
     }

     /**
      * This method is used to cancel a booking.
      * @param bookingId - The ID of the booking to be cancelled
      * @return String - String stating whether or not the booking was cancelled successfully
      */
     public String cancelBooking(int bookingId) {
        Long bookingIdAsLong = Long.valueOf(bookingId);
        try{
            bookingService.cancelBooking(bookingIdAsLong);
        } catch (IllegalArgumentException e) {
            return "FAILURE!";
        }
        return "SUCCESS!";
     }

     /***
      * This method is used to create a booking for an event.
      * @param booking - The booking to be created
      * @param organiserId - The ID of the organiser
      * @return String - String stating whether or not the booking was successful
      */
     public String createBooking(BookingDto booking, int organiserId) {

        if(bookingService.hasConflict(booking.getRoomId(), booking.getStartTime(), booking.getDuration())){
            return "BOOKING CONFLICT!";
        }
        // String eventName = booking.getEventName();
        // RoomDto room = roomService.findRoomById(booking.getRoomId().intValue());


        // Booking newBooking = new Booking(booking.getEventName(), roomService.findRoomById(booking.getRoomId().intValue()), booking.getStartTime(), booking.getDuration(), new Organiser(organiserId));



         return "SUCCESS!";
     }
}
