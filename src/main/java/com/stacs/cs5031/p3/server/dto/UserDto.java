package com.stacs.cs5031.p3.server.dto;

/**
 * UserDto class.
 * This class implements data transfer objects for user entities.
 * It is used for transferring user data to clients without
 * exposing sensitive information.
 */
public class UserDto {
    private final Integer id;
    private final String username;
    private final String name;

    public UserDto(Integer id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }
}