package com.stacs.cs5031.p3.server.model;

import java.util.ArrayList;

import com.stacs.cs5031.p3.server.dto.RoomDto;
import com.stacs.cs5031.p3.server.exception.RoomNotFoundException;
import com.stacs.cs5031.p3.server.service.OrganiserService;
import com.stacs.cs5031.p3.server.service.RoomService;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Admin extends User {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    private String name;
    private String username;
    private String password;
    private RoomService roomService; // Dependency for managing rooms
    private OrganiserService organiserService; // Dependency for managing organisers
    // private AttendeeService attendeeService;

    public Admin() {
    }
    
    //TODO add attendeeservice field
    public Admin(String name, String username, String password, RoomService roomService, OrganiserService organiserService) {
        super(name, username, password);
        this.roomService = roomService;
        this.organiserService = organiserService;
        // this.attendeeService = attendeeService;
    }

    public ArrayList<RoomDto> getAllRooms() {
        return new ArrayList<>(roomService.findAllRooms());
    }
    
    //TODO
    // public ArrayList<Attendee> getAttendees() {
    //     return new ArrayList<>();
    // }

    public ArrayList<Organiser> getOrganisers() {
        return organiserService.getAllOrganisers();
    }

    public boolean addRoom(Room room) {
        try {
            roomService.createRoom(room.getName(), room.getCapacity());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean removeRoom(int roomId) {
        try {
            roomService.deleteRoomById(roomId);
            return true;
        } catch (RoomNotFoundException e) {
            return false;
        }
    }
}
