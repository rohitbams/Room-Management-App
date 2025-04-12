package com.stacs.cs5031.p3.server.dto;
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
    }
}