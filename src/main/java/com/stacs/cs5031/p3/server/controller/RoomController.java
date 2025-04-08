package com.stacs.cs5031.p3.server.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacs.cs5031.p3.server.dto.RoomDTO;
import com.stacs.cs5031.p3.server.exception.RoomNotAvailableException;
import com.stacs.cs5031.p3.server.exception.RoomNotFoundException;
import com.stacs.cs5031.p3.server.service.RoomService;

// receives HTTP request from Client layer
// maps req (JSON body) to DTO objects
// sends DTO objects to service layer
@RestController
@RequestMapping("/rooms/")
public class RoomController {

    private RoomService roomService;

    @GetMapping("/all")
    public List<RoomDTO> getAllRooms() {
        return roomService.findAllRooms();
    }

    @GetMapping("/available")
    public List<RoomDTO> getAvailableRooms() {
        return roomService.findAvailableRooms();
    }

    @PostMapping("/{id}/book")
    public ResponseEntity<RoomDTO> bookRoom(@PathVariable int id) {
        try {
            // service returns DTO
            RoomDTO bookedRoom = roomService.bookRoom(id);
            return ResponseEntity.ok(bookedRoom);
        } catch (RoomNotAvailableException e) {
            return ResponseEntity.badRequest().build();
        } catch (RoomNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/makeAvailable")
    public ResponseEntity<RoomDTO> makeRoomAvailable(@PathVariable int id) {
        try {
            RoomDTO availableRoom = roomService.makeRoomAvailable(id);
            return ResponseEntity.ok(availableRoom);
        } catch (RoomNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
