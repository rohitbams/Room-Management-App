package com.stacs.cs5031.p3.server.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.stacs.cs5031.p3.server.model.Room;
import com.stacs.cs5031.p3.server.service.RoomService;
import com.stacs.cs5031.p3.server.model.Organiser;
import com.stacs.cs5031.p3.server.repository.OrganiserRepository;

@Service
public class OrganiserService {

    private OrganiserRepository organiserRepository;
    private RoomService roomService;
    // private final RoomService roomService;

    // public OrganiserService(OrganiserRepository organiserRepository, RoomService roomService) {
    public OrganiserService(OrganiserRepository organiserRepository, RoomService roomService) {
        this.organiserRepository = organiserRepository;
        this.roomService = roomService;
        //this.roomService = roomService;
    }

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
     * @return ArrayList<Organiser> - List of all the organisers.
     */
    public ArrayList<Organiser> getAllOrganisers() {
        return new ArrayList<Organiser>(organiserRepository.findAll());
    }
    

    /**
     * This method is used to get all the available rooms from the database.
     * @return ArrayList<String> - List of all the available rooms.
     */
    public ArrayList<Room> getAvailableRooms() {
        //return roomService.findAvailableRooms();
        return null;
    }

    // public ArrayList<String> getMyBookings() {
    //     return new ArrayList<String>();
    // }

    // public ArrayList<Booking> getMyBooking(int bookingId) {
    //     return new ArrayList<Booking>();
    // }

    // public String cancelBooking(int bookingId) {
    //     return "Booking cancelled";
    // }

    // public String createBooking(Booking booking, int organiserId) {
    //     return "Booking created";
    // }
}
