package com.stacs.cs5031.p3.server;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Room {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private int capacity;
    private boolean availability; // If available, return true

    // this constructor exists only because JPA requires a default constructor
    // not used directly
    protected Room() {}
    
    // the constructor used to create Room instances, and save them to database
    public Room(String name, int capacity, boolean availability) {
        this.name = name;
        this.capacity = capacity;
        this.availability = availability;
    }

    public boolean isItBooked() {
        return !availability;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }
}