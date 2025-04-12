package com.stacs.cs5031.p3.server.exception;

/**
 * This exception is thrown when a user with a given ID or username does not exist.
 */
public class UserNotFoundExcpetion extends RuntimeException {
    public UserNotFoundExcpetion(Integer id) {
        super("User with id: " + id + " does not exist");
    }
    public UserNotFoundExcpetion(String username) {
        super("User with username: " + username + " does not exist");
    }
}
