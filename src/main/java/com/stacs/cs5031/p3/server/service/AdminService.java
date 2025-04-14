package com.stacs.cs5031.p3.server.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.stacs.cs5031.p3.server.dto.RoomDto;
import com.stacs.cs5031.p3.server.exception.RoomNotFoundException;
import com.stacs.cs5031.p3.server.model.Attendee;
import com.stacs.cs5031.p3.server.model.Organiser;
import com.stacs.cs5031.p3.server.model.Room;
import com.stacs.cs5031.p3.server.repository.AdminRepository;

public class AdminService {
    private AdminRepository adminRepository;
    private final RoomService roomService;
    private final AttendeeService attendeeService;
    private final OrganiserService organiserService;
    
    @Autowired
    public AdminService(AdminRepository adminRepository, RoomService roomService, AttendeeService attendeeService, OrganiserService organiserService) {
        this.adminRepository = adminRepository;
        this.roomService = roomService;
        this.attendeeService = attendeeService;
        this.organiserService = organiserService;
    }

    public ArrayList<RoomDto> getAllRooms() {
        return new ArrayList<>(roomService.findAllRooms());
    }
    
    public ArrayList<Attendee> getAttendees() {
        return new ArrayList<>(attendeeService.getAllAttendees());
    }

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
