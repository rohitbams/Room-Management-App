package com.stacs.cs5031.p3.server.mapper;

import com.stacs.cs5031.p3.server.dto.BookingDTO;
import com.stacs.cs5031.p3.server.model.Booking;

import java.util.List;
import java.util.stream.Collectors;

public final class BookingDtoMapper {
    private BookingDtoMapper() {
        // Prevent instantiation of utility class
    }

    public static BookingDTO mapToDTO(Booking booking) {
        return new BookingDTO(
            (long) booking.getId(),
            booking.getName(),
            (long) booking.getRoom().getID(),
            booking.getRoom().getName(),
            booking.getStartTime(),
            booking.getDuration(),
            (long) booking.getOrganiser().getId(),
            booking.getOrganiser().getFullName(),
           // booking.getAttendees().size(),
            booking.getRoom().getCapacity()
        );
    }
    
    public static List<BookingDTO> mapToDTOList(List<Booking> bookings) {
        return bookings.stream()
                .map(BookingDtoMapper::mapToDTO)
                .collect(Collectors.toList());
    }
}