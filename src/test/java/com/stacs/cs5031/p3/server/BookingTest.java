package com.stacs.cs5031.p3.server;

public package com.stacs.cs5031.p3.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Booking class.
 * Tests all the functionality of the Booking class.
 */
public class BookingTest {
    
    private Room room;
    private Organiser organiser;
    private Booking booking;
    private Attendee attendee1;
    private Attendee attendee2;
    private Attendee attendee3;
    private Date startTime;
    
    /**
     * Sets up test objects before each test.
     */
    @BeforeEach
    public void setUp() {
        // Create a room with a capacity of 2
        room = new Room(2);
        // Set ID and name using reflection (simulating JPA)
        ReflectionTestUtils.setField(room, "id", 1L);
        ReflectionTestUtils.setField(room, "name", "Conference Room A");
        
        // Create an organiser
        organiser = new Organiser("organiser1", "password", "John Smith", "john@example.com");
        // Set ID using reflection (simulating JPA)
        ReflectionTestUtils.setField(organiser, "id", 1L);
        
        // Create a start time (current time)
        startTime = new Date();
        
        // Create a booking
        booking = new Booking("Team Meeting", room, startTime, 60, organiser);
        // Set ID using reflection (simulating JPA)
        ReflectionTestUtils.setField(booking, "id", 1L);
        
        // Create some attendees
        attendee1 = new Attendee("attendee1", "password", "Alice Johnson", "alice@example.com");
        attendee2 = new Attendee("attendee2", "password", "Bob Williams", "bob@example.com");
        attendee3 = new Attendee("attendee3", "password", "Charlie Brown", "charlie@example.com");
        // Set IDs using reflection (simulating JPA)
        ReflectionTestUtils.setField(attendee1, "id", 2L);
        ReflectionTestUtils.setField(attendee2, "id", 3L);
        ReflectionTestUtils.setField(attendee3, "id", 4L);
    }
    
    /**
     * Test for the constructor and basic getters.
     */
    @Test
    public void testConstructorAndGetters() {
        assertEquals(1L, booking.getId());
        assertEquals("Team Meeting", booking.getName());
        assertEquals(room, booking.getRoom());
        assertEquals(startTime, booking.getStartTime());
        assertEquals(60, booking.getDuration());
        assertEquals(organiser, booking.getOrganiser());
        assertEquals(0, booking.getAttendees().size());
    }
    
    /**
     * Test for the isThereSpace method.
     */
    @Test
    public void testIsThereSpace() {
        // Initially there should be space (room capacity is 2)
        assertTrue(booking.isThereSpace());
        
        // Add one attendee
        booking.addAttendee(attendee1);
        assertTrue(booking.isThereSpace());
        
        // Add second attendee
        booking.addAttendee(attendee2);
        assertFalse(booking.isThereSpace());
    }
    
    /**
     * Test for adding attendees.
     */
    @Test
    public void testAddAttendee() {
        // Add first attendee
        assertTrue(booking.addAttendee(attendee1));
        assertEquals(1, booking.getAttendees().size());
        assertTrue(booking.getAttendees().contains(attendee1));
        
        // Add second attendee
        assertTrue(booking.addAttendee(attendee2));
        assertEquals(2, booking.getAttendees().size());
        assertTrue(booking.getAttendees().contains(attendee2));
        
        // Try to add third attendee (should fail as room capacity is 2)
        assertFalse(booking.addAttendee(attendee3));
        assertEquals(2, booking.getAttendees().size());
        assertFalse(booking.getAttendees().contains(attendee3));
    }
    
    /**
     * Test for removing attendees.
     */
    @Test
    public void testRemoveAttendee() {
        // Add attendees
        booking.addAttendee(attendee1);
        booking.addAttendee(attendee2);
        
        // Remove first attendee
        assertTrue(booking.removeAttendee(attendee1));
        assertEquals(1, booking.getAttendees().size());
        assertFalse(booking.getAttendees().contains(attendee1));
        
        // Try to remove an attendee that's not registered
        assertFalse(booking.removeAttendee(attendee3));
        
        // Remove second attendee
        assertTrue(booking.removeAttendee(attendee2));
        assertEquals(0, booking.getAttendees().size());
    }
    
    /**
     * Test for the getEndTime method.
     */
    @Test
    public void testGetEndTime() {
        // Calculate expected end time (start time + 60 minutes)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.MINUTE, 60);
        Date expectedEndTime = calendar.getTime();
        
        // Check end time calculation
        long expectedTimeMillis = expectedEndTime.getTime();
        long actualTimeMillis = booking.getEndTime().getTime();
        
        // Allow for a small difference due to calculation timing
        long difference = Math.abs(expectedTimeMillis - actualTimeMillis);
        assertTrue(difference < 1000, "End time calculation should be within 1 second");
    }
    
    /**
     * Test for the overlaps method with overlapping bookings.
     */
    @Test
    public void testOverlapsWithOverlapping() {
        // Create a calendar to manipulate dates
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        
        // Create dates for another booking that overlaps
        // Start time is 30 minutes after the first booking starts
        calendar.add(Calendar.MINUTE, 30);
        Date overlappingStart = calendar.getTime();
        
        // Create another booking in the same room that starts during the first booking
        Booking overlappingBooking = new Booking("Overlapping Meeting", room, overlappingStart, 60, organiser);
        
        // Check that the bookings overlap
        assertTrue(booking.overlaps(overlappingBooking));
        assertTrue(overlappingBooking.overlaps(booking));
    }
    
    /**
     * Test for the overlaps method with non-overlapping bookings.
     */
    @Test
    public void testOverlapsWithNonOverlapping() {
        // Create a calendar to manipulate dates
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        
        // Create dates for another booking that doesn't overlap
        // Start time is 2 hours after the first booking starts (well after it ends)
        calendar.add(Calendar.HOUR, 2);
        Date nonOverlappingStart = calendar.getTime();
        
        // Create another booking in the same room that starts after the first booking ends
        Booking nonOverlappingBooking = new Booking("Non-Overlapping Meeting", room, nonOverlappingStart, 60, organiser);
        
        // Check that the bookings don't overlap
        assertFalse(booking.overlaps(nonOverlappingBooking));
        assertFalse(nonOverlappingBooking.overlaps(booking));
    }
    
    /**
     * Test for the overlaps method with bookings in different rooms.
     */
    @Test
    public void testOverlapsWithDifferentRooms() {
        // Create another room
        Room anotherRoom = new Room(10);
        ReflectionTestUtils.setField(anotherRoom, "id", 2L);
        ReflectionTestUtils.setField(anotherRoom, "name", "Conference Room B");
        
        // Create a booking with the same time but in a different room
        Booking differentRoomBooking = new Booking("Different Room Meeting", anotherRoom, startTime, 60, organiser);
        
        // Check that the bookings don't overlap (because they're in different rooms)
        assertFalse(booking.overlaps(differentRoomBooking));
        assertFalse(differentRoomBooking.overlaps(booking));
    }
    
    /**
     * Test for the setters.
     */
    @Test
    public void testSetters() {
        // Create another room and new date
        Room anotherRoom = new Room(10);
        ReflectionTestUtils.setField(anotherRoom, "id", 2L);
        ReflectionTestUtils.setField(anotherRoom, "name", "Conference Room B");
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date newDate = calendar.getTime();
        
        // Use setters
        booking.setEventName("Updated Meeting");
        booking.setRoom(anotherRoom);
        booking.setStartTime(newDate);
        booking.setDuration(90);
        
        // Check that the values were updated
        assertEquals("Updated Meeting", booking.getName());
        assertEquals(anotherRoom, booking.getRoom());
        assertEquals(newDate, booking.getStartTime());
        assertEquals(90, booking.getDuration());
    }
    
    /**
     * Test the organiser's bookings management.
     */
    @Test
    public void testOrganiserBookingsManagement() {
        // Initially, the organiser should have no bookings
        assertEquals(0, organiser.getCreatedBookings().size());
        
        // Add the booking to the organiser
        organiser.addBooking(booking);
        assertEquals(1, organiser.getCreatedBookings().size());
        assertTrue(organiser.getCreatedBookings().contains(booking));
        
        // Remove the booking
        assertTrue(organiser.removeBooking(booking));
        assertEquals(0, organiser.getCreatedBookings().size());
    }
    
    /**
     * Test the attendee's bookings management.
     */
    @Test
    public void testAttendeeBookingsManagement() {
        // Create a room with higher capacity
        Room largeRoom = new Room(10);
        ReflectionTestUtils.setField(largeRoom, "id", 3L);
        ReflectionTestUtils.setField(largeRoom, "name", "Large Room");
        
        Booking largeBooking = new Booking("Large Meeting", largeRoom, startTime, 60, organiser);
        ReflectionTestUtils.setField(largeBooking, "id", 2L);
        
        // Initially, the attendee should have no bookings
        assertEquals(0, attendee1.getRegisteredBookings().size());
        
        // Register for the booking
        assertTrue(attendee1.registerForBooking(booking));
        assertEquals(1, attendee1.getRegisteredBookings().size());
        assertTrue(attendee1.getRegisteredBookings().contains(booking));
        assertTrue(booking.getAttendees().contains(attendee1));
        
        // Register for another booking
        assertTrue(attendee1.registerForBooking(largeBooking));
        assertEquals(2, attendee1.getRegisteredBookings().size());
        assertTrue(attendee1.getRegisteredBookings().contains(largeBooking));
        
        // Unregister from the first booking
        assertTrue(attendee1.unregisterFromBooking(booking));
        assertEquals(1, attendee1.getRegisteredBookings().size());
        assertFalse(attendee1.getRegisteredBookings().contains(booking));
        assertFalse(booking.getAttendees().contains(attendee1));
    }
    
    /**
     * Test the equals and hashCode methods of Booking.
     */
    @Test
    public void testEqualsAndHashCode() {
        // Create a booking with the same ID
        Booking sameIdBooking = new Booking("Another Meeting", room, startTime, 90, organiser);
        ReflectionTestUtils.setField(sameIdBooking, "id", 1L);
        
        // Create a booking with a different ID
        Booking differentIdBooking = new Booking("Different Meeting", room, startTime, 30, organiser);
        ReflectionTestUtils.setField(differentIdBooking, "id", 2L);
        
        // Test equals method
        assertEquals(booking, booking);  // Same object reference
        assertEquals(booking, sameIdBooking);  // Different objects, same ID
        assertNotEquals(booking, differentIdBooking);  // Different IDs
        assertNotEquals(booking, null);  // Null comparison
        assertNotEquals(booking, new Object());  // Different types
        
        // Test hashCode method
        assertEquals(booking.hashCode(), sameIdBooking.hashCode());
        assertNotEquals(booking.hashCode(), differentIdBooking.hashCode());
    }
} {
    
}
