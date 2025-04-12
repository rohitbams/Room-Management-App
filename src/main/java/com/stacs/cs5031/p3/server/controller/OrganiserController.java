//package com.stacs.cs5031.p3.server.controller;
//
//import java.util.ArrayList;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.stacs.cs5031.p3.server.model.Organiser;
//import com.stacs.cs5031.p3.server.repository.RoomRepository;
//import com.stacs.cs5031.p3.server.service.OrganiserService;
//
//@RestController
//public class OrganiserController {
//
//    @Autowired
//    private OrganiserService organiserService;
//
//
//    @PostMapping("/organiser/create-organiser")
//    public ResponseEntity<String> createOrganiser(@RequestBody Organiser organiser) {
//        try{
//            organiserService.createOrganiser(organiser);
//            return ResponseEntity.status(HttpStatus.CREATED).body("SUCCESS!");
//        } catch (IllegalArgumentException e) { //if organiser credentials are invalid
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        } catch(Exception e) { //if there is an error in the server
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error!");
//        }
//    }
//
//    @GetMapping(value = "/organisers", produces = { "application/json" })
//    public ResponseEntity<ArrayList<Organiser>> getAllOrganisers() {
//        try{
//            return ResponseEntity.status(HttpStatus.OK).body(organiserService.getAllOrganisers());
//        }catch(Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//    // @GetMapping(value = "/organiser/available-rooms", produces = { "application/json" })
//    // public ResponseEntity<ArrayList<String>> getAvailableRooms() {
//    //     // try{
//    //     //     return ResponseEntity.status(HttpStatus.OK).body(roomRepository.findByAvailbility(true));
//    //     // }catch(Exception e) {
//    //     //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//    //     // }
//    // }
//
//    // @GetMapping(value = "/organiser/my-bookings", produces = { "application/json" })
//    // public ResponseEntity<ArrayList<String>> getMyBookings() {
//
//    // }
//
//    // @GetMapping(value = "/organiser/my-bookings/{bookingId}", produces = { "application/json" })
//    // public ResponseEntity<Booking> getMyBooking(@PathVariable int bookingId) {
//    //   // return ResponseEntity.status(HttpStatus.OK).body(bookingService.getBookingById(bookingId));
//    // }
//
//}
