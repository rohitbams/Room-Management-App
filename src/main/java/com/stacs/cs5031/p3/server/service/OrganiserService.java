package com.stacs.cs5031.p3.server.service;

import java.util.ArrayList;
import java.util.List;

import com.stacs.cs5031.p3.server.model.Booking;
import org.springframework.stereotype.Service;

import com.stacs.cs5031.p3.server.dto.OrganiserDto;
import com.stacs.cs5031.p3.server.dto.RoomDto;
import com.stacs.cs5031.p3.server.model.Organiser;
import com.stacs.cs5031.p3.server.repository.OrganiserRepository;

/**
 * 
 * @author 190031593
 */
@Service
public class OrganiserService {

    private OrganiserRepository organiserRepository;
    private RoomService roomService;
    // private final RoomService roomService;

    public OrganiserService(OrganiserRepository organiserRepository, RoomService roomService) {
        this.organiserRepository = organiserRepository;
        this.roomService = roomService;
    }

    /**
     * This method is used to create an organiser and save it to the database.
     * @param organiser - The organiser to be created.
     * @return String - The status of the operation.
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

     public String cancelBooking(int bookingId) {
         return "Booking cancelled";
     }

     public String createBooking(Booking booking, int organiserId) {
         return "Booking created";
     }
}
