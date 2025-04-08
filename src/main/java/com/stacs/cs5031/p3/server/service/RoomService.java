package com.stacs.cs5031.p3.server.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.stacs.cs5031.p3.server.dto.RoomDTO;
import com.stacs.cs5031.p3.server.exception.RoomNotAvailableException;
import com.stacs.cs5031.p3.server.exception.RoomNotFoundException;
import com.stacs.cs5031.p3.server.model.Room;
import com.stacs.cs5031.p3.server.repository.RoomRepository;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    
    public RoomDTO createRoom(int capacity) throws IllegalArgumentException {
        // validate user-provided data
        if (capacity <= 1) {
            throw new IllegalArgumentException("Room capacity must be at least 1");
        }
        Room roomEntity = roomRepository.save(new Room("Test Room", capacity));
        return mapToDTO(roomEntity);
    }

    public RoomDTO findRoomById(int id) throws RoomNotFoundException {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found: " + id));
        return mapToDTO(room);
    }

    // FIXME fix authorisation
    // Only admin can view all rooms
    @PreAuthorize("hasRole('ADMIN')")
    public List<RoomDTO> findAllRooms(){
        return StreamSupport.stream(roomRepository.findAll().spliterator(), false)
        .map(this::mapToDTO)
        .collect(Collectors.toList());
    }
    
    public List<RoomDTO> findAvailableRooms() {
        return StreamSupport.stream(roomRepository.findByAvailability(true).spliterator(), false)
        .map(this::mapToDTO)
        .collect(Collectors.toList());
    }
    
    public RoomDTO bookRoom(int id) throws RoomNotAvailableException, RoomNotFoundException {
        Room roomEntity = roomRepository.findById(id)
        .orElseThrow(() -> new RoomNotFoundException("Room not found: " + id));
        
        // if room is already booked, throw exception
        if (!roomEntity.isAvailable()) {
            throw new RoomNotAvailableException("Room " + id + " is already booked");
        }
        
        // if room is not booked
        roomEntity.bookRoom();
        roomRepository.save(roomEntity);
        return mapToDTO(roomEntity);
    }
    
    public RoomDTO makeRoomAvailable(int id) throws RoomNotFoundException{
        Room roomEntity = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found: " + id));

        // if room is already available, do nothing
        if (!roomEntity.isAvailable()) {
            roomEntity.makeAvailable();
            roomRepository.save(roomEntity);
        }

        return mapToDTO(roomEntity);
    }

    private RoomDTO mapToDTO(Room room) {
        return new RoomDTO(room.getID(), room.getName(), room.getCapacity(), room.isAvailable());
    }

    // FIXME fix authorisation
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRoomById(int id) throws RoomNotFoundException {
        Room roomEntity = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found: " + id));

        roomRepository.delete(roomEntity);
    }
}
