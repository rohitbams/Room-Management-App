package com.stacs.cs5031.p3.server.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.stacs.cs5031.p3.server.dto.RoomDto;
import com.stacs.cs5031.p3.server.exception.RoomNotAvailableException;
import com.stacs.cs5031.p3.server.exception.RoomNotFoundException;
import com.stacs.cs5031.p3.server.mapper.RoomDtoMapper;
import com.stacs.cs5031.p3.server.model.Room;
import com.stacs.cs5031.p3.server.repository.RoomRepository;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    
    public RoomDto createRoomDto(String name, int capacity) throws IllegalArgumentException {
        // validate user-provided data
        if (capacity <= 1) {
            throw new IllegalArgumentException("Room capacity must be at least 1");
        }
        Room roomEntity = roomRepository.save(new Room(name, capacity));
        return RoomDtoMapper.mapToDTO(roomEntity);
    }


    public Room findRoomById(int id) throws RoomNotFoundException {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found: " + id));
    }

    public RoomDto findRoomDtoById(int id) throws RoomNotFoundException {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found: " + id));
        return RoomDtoMapper.mapToDTO(room);
    }

    // FIXME fix authorisation
    // Only admin can view all rooms
    @PreAuthorize("hasRole('ADMIN')")
    public List<RoomDto> findAllRooms(){
        return StreamSupport.stream(roomRepository.findAll().spliterator(), false)
        .map(RoomDtoMapper::mapToDTO)
        .collect(Collectors.toList());
    }
    
    public List<RoomDto> findAvailableRooms() {
        return StreamSupport.stream(roomRepository.findByAvailability(true).spliterator(), false)
        .map(RoomDtoMapper::mapToDTO)
        .collect(Collectors.toList());
    }
    
    public RoomDto bookRoom(int id) throws RoomNotAvailableException, RoomNotFoundException {
        Room roomEntity = roomRepository.findById(id)
        .orElseThrow(() -> new RoomNotFoundException("Room not found: " + id));
        
        // if room is already booked, throw exception
        if (!roomEntity.isAvailable()) {
            throw new RoomNotAvailableException("Room " + id + " is already booked");
        }
        
        // if room is not booked
        roomEntity.bookRoom();
        roomRepository.save(roomEntity);
        return RoomDtoMapper.mapToDTO(roomEntity);
    }
    
    public RoomDto makeRoomAvailable(int id) throws RoomNotFoundException{
        Room roomEntity = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found: " + id));

        // if room is already available, do nothing
        if (!roomEntity.isAvailable()) {
            roomEntity.makeAvailable();
            roomRepository.save(roomEntity);
        }

        return RoomDtoMapper.mapToDTO(roomEntity);
    }

    // FIXME fix authorisation
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRoomById(int id) throws RoomNotFoundException {
        Room roomEntity = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found: " + id));

        roomRepository.delete(roomEntity);
    }
}
