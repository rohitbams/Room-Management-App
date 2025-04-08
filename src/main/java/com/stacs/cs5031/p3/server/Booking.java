
package com.stacs.cs5031.p3.server;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Represents a booking/event in the room booking system.
 * Contains information about the event, its location, timing, organizer, and attendees.
 */
@Entity
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String eventName;
    
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    
    private int duration;  // duration in minutes
    
    @ManyToOne
    @JoinColumn(name = "organiser_id")
    private Organiser organiser;
    
    @ManyToMany
    @JoinTable(
        name = "booking_attendees",
        joinColumns = @JoinColumn(name = "booking_id"),
        inverseJoinColumns = @JoinColumn(name = "attendee_id")
    )
    private List<Attendee> attendees = new ArrayList<>();
    
    /**
     * Default constructor required by JPA.
     */
    protected Booking() {
        // Required by JPA
    }
    
    /**
     * Constructor for creating a new booking.
     * 
     * @param eventName Name of the event
     * @param room Room where the event will take place
     * @param startTime Start time and date of the event
     * @param duration Duration of the event in minutes
     * @param organiser User who organized the event
     */
    public Booking(String eventName, Room room, Date startTime, int duration, Organiser organiser) {
        this.eventName = eventName;
        this.room = room;
        this.startTime = startTime;
        this.duration = duration;
        this.organiser = organiser;
    }
    
    /**
     * Checks if there is space available in the room for more attendees.
     * 
     * @return true if there is space, false if the room is at capacity
     */
    public boolean isThereSpace() {
        return attendees.size() < room.getCapacity();
    }
    
    /**
     * Adds an attendee to the booking if there is space available.
     * 
     * @param attendee The attendee to add
     * @return true if the attendee was added successfully, false if there was no space
     */
    public boolean addAttendee(Attendee attendee) {
        if (isThereSpace()) {
            attendees.add(attendee);
            return true;
        }
        return false;
    }
    
    /**
     * Removes an attendee from the booking.
     * 
     * @param attendee The attendee to remove
     * @return true if the attendee was removed, false if they were not registered
     */
    public boolean removeAttendee(Attendee attendee) {
        return attendees.remove(attendee);
    }
    
    /**
     * Gets the room where the event will take place.
     * 
     * @return The room object
     */
    public Room getRoom() {
        return room;
    }
    
    /**
     * Gets the start time of the event.
     * 
     * @return The start time as a Date object
     */
    public Date getStartTime() {
        return startTime;
    }
    
    /**
     * Gets the duration of the event in minutes.
     * 
     * @return The duration in minutes
     */
    public int getDuration() {
        return duration;
    }
    
    /**
     * Gets the organiser of the event.
     * 
     * @return The organiser object
     */
    public Organiser getOrganiser() {
        return organiser;
    }
    
    /**
     * Gets the name of the event.
     * 
     * @return The event name
     */
    public String getName() {
        return eventName;
    }
    
    /**
     * Gets the list of attendees for this event.
     * 
     * @return A List of Attendee objects
     */
    public List<Attendee> getAttendees() {
        return attendees;
    }
    
    /**
     * Gets the ID of the booking.
     * 
     * @return The booking ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Sets a new name for the event.
     * 
     * @param eventName The new event name
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    
    /**
     * Changes the room for the event.
     * 
     * @param room The new room
     */
    public void setRoom(Room room) {
        this.room = room;
    }
    
    /**
     * Changes the start time of the event.
     * 
     * @param startTime The new start time
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    /**
     * Changes the duration of the event.
     * 
     * @param duration The new duration in minutes
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    /**
     * Calculates the end time of the event based on start time and duration.
     * 
     * @return The end time as a Date object
     */
    public Date getEndTime() {
        // Create a new Date object to avoid modifying the original
        Date endTime = new Date(startTime.getTime());
        // Add duration in milliseconds
        endTime.setTime(endTime.getTime() + (duration * 60 * 1000));
        return endTime;
    }
    
    /**
     * Checks if this booking overlaps with another booking.
     * 
     * @param other The other booking to check against
     * @return true if there is an overlap, false otherwise
     */
    public boolean overlaps(Booking other) {
        // If the rooms are different, there's no overlap
        if (!this.room.equals(other.room)) {
            return false;
        }
        
        Date thisEnd = this.getEndTime();
        Date otherEnd = other.getEndTime();
        
        // Check if one booking starts during the other
        return (this.startTime.before(otherEnd) && thisEnd.after(other.startTime));
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Booking [id=" + id + ", eventName=" + eventName + ", room=" + room.getName() 
                + ", startTime=" + startTime + ", duration=" + duration + " mins, organiser=" 
                + organiser.getUsername() + ", attendees=" + attendees.size() + "]";
    }
}
