package com.stacs.cs5031.p3.server.mapper;

import com.stacs.cs5031.p3.server.dto.RoomDto;
import com.stacs.cs5031.p3.server.model.Room;

public final class RoomDtoMapper {
    private RoomDtoMapper() {
        // Prevent instantiation of utility class
    }

    public static RoomDto mapToDTO(Room room) {
        return new RoomDto(
            room.getID(),
            room.getName(),
            room.getCapacity(),
            room.isAvailable()
        );
    }
}
