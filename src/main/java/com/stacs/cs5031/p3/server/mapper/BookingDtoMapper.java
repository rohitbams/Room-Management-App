package com.stacs.cs5031.p3.server.mapper;

import com.stacs.cs5031.p3.server.dto.BookingDto;
import com.stacs.cs5031.p3.server.dto.AttendeeDto;
import com.stacs.cs5031.p3.server.model.Booking;


import java.util.List;
import java.util.stream.Collectors;

public final class BookingDtoMapper {

    private BookingDtoMapper() {
        // Prevent instantiation of utility class
    }

    public static BookingDto mapToDTO(Booking booking) {
        List<AttendeeDto> attendeeDtos = AttendeeDtoMapper.mapToDtoList(booking.getAttendees());

        return new BookingDto(
                (long) booking.getId(),
                booking.getName(),
                (long) booking.getRoom().getID(),
                booking.getRoom().getName(),
                booking.getStartTime(),
                booking.getDuration(),
                (long) booking.getOrganiser().getId(),
                booking.getOrganiser().getName(),
                attendeeDtos,
                attendeeDtos.size(),
                booking.getRoom().getCapacity()
        );
    }

    public static List<BookingDto> mapToDTOList(List<Booking> bookings) {
        return bookings.stream()
                .map(BookingDtoMapper::mapToDTO)
                .collect(Collectors.toList());
    }
}