package com.stacs.cs5031.p3.server.mapper;

import com.stacs.cs5031.p3.server.dto.UserDto;
import com.stacs.cs5031.p3.server.model.User;

/**
 * UserDtoMapper utility class.
 *
 */
public final class UserDtoMapper {
    private UserDtoMapper() {
    }

    public static UserDto mapToDTO(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getName()
        );
    }
}