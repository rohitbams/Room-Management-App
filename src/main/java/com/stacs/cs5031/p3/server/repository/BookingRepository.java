package com.stacs.cs5031.p3.server.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stacs.cs5031.p3.server.model.Booking;

// database operations for bookings
@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>  {

    // Find bookings for a specific room
    public List<Booking> findByRoomId(int roomId);
    
    // Find bookings created by a specific organiser
    public List<Booking> findByOrganiserId(int organiserId);
    
    // Find bookings an attendee is registered for
    @Query("SELECT b FROM Booking b JOIN b.attendees a WHERE a.id = :attendeeId")
    public List<Booking> findByAttendeeId(@Param("attendeeId") int attendeeId);
    
    // Find bookings within a date range
    @Query("SELECT b FROM Booking b WHERE b.startTime >= :start AND b.startTime <= :end")
    public List<Booking> findByDateRange(@Param("start") Date start, @Param("end") Date end);
    
    // Find conflicting bookings for a room
    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId AND " +
           "((b.startTime BETWEEN :start AND :end) OR " +
           "(b.startTime <= :start AND FUNCTION('DATEADD', MINUTE, b.duration, b.startTime) >= :start))")
    public List<Booking> findConflictingBookings(
        @Param("roomId") int roomId,
        @Param("start") Date start,
        @Param("end") Date end);
}