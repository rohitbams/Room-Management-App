package com.stacs.cs5031.p3.server.exception;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(String message) {
        super(message);
    }

    public RoomNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoomNotFoundException(int roomId) {
        super("Room not found with ID: " + roomId);
    }
}
