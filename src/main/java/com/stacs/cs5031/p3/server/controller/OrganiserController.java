package com.stacs.cs5031.p3.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stacs.cs5031.p3.server.dto.BookingDto;
import com.stacs.cs5031.p3.server.dto.OrganiserDto;
import com.stacs.cs5031.p3.server.dto.RoomDto;
import com.stacs.cs5031.p3.server.model.Organiser;
import com.stacs.cs5031.p3.server.repository.RoomRepository;
import com.stacs.cs5031.p3.server.service.OrganiserService;
import com.stacs.cs5031.p3.server.service.RoomService;

@RestController
public class OrganiserController {

    @Autowired
    private OrganiserService organiserService;
    @Autowired
    private RoomService roomService;


    @PostMapping("/organiser/create-organiser")
    public ResponseEntity<String> createOrganiser(@RequestBody Organiser organiser) {
        try{
            organiserService.createOrganiser(organiser);
            return ResponseEntity.status(HttpStatus.CREATED).body("SUCCESS!");
        } catch (IllegalArgumentException e) { //if organiser credentials are invalid
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch(Exception e) { //if there is an error in the server
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error!");
        }
    }

    /**
     * TODO: do i need this????
     * @return
     */
    @GetMapping(value = "/organisers", produces = { "application/json" })
    public ResponseEntity<ArrayList<OrganiserDto>> getAllOrganisers() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(organiserService.getAllOrganisers());
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value = "/organiser/available-rooms", produces = { "application/json" })
    public ResponseEntity<ArrayList<RoomDto>> findAvailableRooms() {
        try{
            ArrayList<RoomDto> availableRooms =new ArrayList<>(roomService.findAvailableRooms());
            return ResponseEntity.status(HttpStatus.OK).body(availableRooms);
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // @GetMapping(value = "/organiser/my-bookings", produces = { "application/json" })
    // public ResponseEntity<ArrayList<BookingDto>> getMyBookings() {

    // }

    // @GetMapping(value = "/organiser/{organiserId}/my-bookings/{bookingId}", produces = { "application/json" })
    // public ResponseEntity<Booking> getBookingDetails(@PathVariable int bookingId, @PathVariable int organiserId) {
    //   // return ResponseEntity.status(HttpStatus.OK).body(bookingService.getBookingById(bookingId));
    // }

    /**
     * 
     * @param bookingId
     * @return
     */
    public ResponseEntity<String> cancelBooking(int bookingId) {
        try{
            organiserService.createBooking(request, organiserId);
            return ResponseEntity.status(HttpStatus.CREATED).body("SUCCESS!");
        } catch (Exception e) { //if booking request is invalid
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * This creates a new book
     * @param request
     * @param organiserId
     * @return
     */
    @PostMapping("/organiser/create-booking/{organiserId}")
    public ResponseEntity<String> createBooking(@RequestBody BookingDto.BookingRequest request, @PathVariable int organiserId) {
        try{
            organiserService.createBooking(request, organiserId);
            return ResponseEntity.status(HttpStatus.CREATED).body("SUCCESS!");
        } catch (Exception e) { //if booking request is invalid
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



}
