package com.stacs.cs5031.p3.server.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacs.cs5031.p3.server.dto.RoomDto;
import com.stacs.cs5031.p3.server.model.Attendee;
import com.stacs.cs5031.p3.server.model.Organiser;
import com.stacs.cs5031.p3.server.model.Room;
import com.stacs.cs5031.p3.server.service.AdminService;

@RestController
@RequestMapping("/admin/")
public class AdminController {
    
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    
    @GetMapping("/rooms")
    public ResponseEntity<ArrayList<RoomDto>> getAllRooms() {
        ArrayList<RoomDto> rooms = adminService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }
   
    @GetMapping("/attendees")
    public ResponseEntity<ArrayList<Attendee>> getAllAttendees() {
        ArrayList<Attendee> attendees = adminService.getAttendees();
        return ResponseEntity.ok(attendees);
    }

    @GetMapping("/organisers")
    public ResponseEntity<ArrayList<Organiser>> getAllOrganisers() {
        ArrayList<Organiser> organisers = adminService.getOrganisers();
        return ResponseEntity.ok(organisers);
    }

    @PostMapping("/rooms")
    public ResponseEntity<String> addRoom(@RequestBody Room room) {
        boolean success = adminService.addRoom(room);
        if (success) {
            return ResponseEntity.ok("Room added successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to add room");
        }
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<String> removeRoom(@PathVariable int roomId) {
        boolean success = adminService.removeRoom(roomId);
        if (success) {
            return ResponseEntity.ok("Room removed successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to remove room");
        }
    }
}
