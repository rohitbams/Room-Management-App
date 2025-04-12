package com.stacs.cs5031.p3.server.mapper;

<<<<<<< HEAD
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
=======
import com.stacs.cs5031.p3.server.dto.UserDTO;
import com.stacs.cs5031.p3.server.model.Attendee;
import com.stacs.cs5031.p3.server.model.Organiser;
import com.stacs.cs5031.p3.server.model.User;

public final class UserDtoMapper {
    private UserDtoMapper() {
        // Prevent instantiation of utility class
    }

    public static UserDTO mapToDTO(User user) {
        String userType = "USER";
        if (user instanceof Attendee) {
            userType = "ATTENDEE";
        } else if (user instanceof Organiser) {
            userType = "ORGANISER";
        }
        
        return new UserDTO(
            (long) user.getId(),
            user.getUsername(),
            user.getFullName(),
            userType
>>>>>>> 9a7fae746b007df9afc2a3e326beb6e2a5e32e77
        );
    }
}