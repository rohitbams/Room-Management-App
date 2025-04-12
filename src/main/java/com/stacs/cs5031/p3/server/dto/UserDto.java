package com.stacs.cs5031.p3.server.dto;
<<<<<<< HEAD

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
=======
/**
 * Data Transfer Object for User entities.
 */
public class UserDTO {
    private Long id;
    private String username;
    private String fullName;
    private String userType;  // "ATTENDEE" or "ORGANISER"
    
    // Default constructor
    public UserDTO() {
    }
    
    // Parameterized constructor
    public UserDTO(Long id, String username, String fullName, String userType) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.userType = userType;
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getUserType() {
        return userType;
    }
    
    public void setUserType(String userType) {
        this.userType = userType;
>>>>>>> 9a7fae746b007df9afc2a3e326beb6e2a5e32e77
    }
}