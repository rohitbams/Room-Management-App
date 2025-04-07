package com.stacs.cs5031.p3.server.dto;

public class RoomDTO {
    // immutable DTO
    private final int id;
    private final String name;
    private final int capacity;
    private final boolean availability;

    public RoomDTO(int id, String name, int capacity, boolean availability) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.availability = availability;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isAvailable() {
        return availability;
    }
}
