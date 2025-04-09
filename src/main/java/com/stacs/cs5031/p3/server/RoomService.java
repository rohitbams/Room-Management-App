package com.stacs.cs5031.p3.server;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    // TODO should throw exception
    public boolean createRoom(int capacity) {
        Room room = new Room(capacity);
        roomRepository.save(room);
        return (roomRepository.findById(room.getID()) != null);
    }

    public String getName(Room room) {
        return null;
    }

    public int getID(Room room) {
        return 0;
    }

    public int getCapacity(Room room) {
        return 0;
    }

    public Room findRoomById(int i) {
        return null;
    }

    public ArrayList<Room> findAllRooms() {
        return null;
    }

    public ArrayList<Room> findAvailableRooms() {
        return null;
    }

    // returns true if success
    public boolean bookRoom() {
        return false;
    }

    public boolean makeRoomAvailable() {
        return false;
    }
    
}
