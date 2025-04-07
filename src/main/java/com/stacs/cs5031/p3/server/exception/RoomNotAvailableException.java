package com.stacs.cs5031.p3.server.exception;

public class RoomNotAvailableException extends RuntimeException {
    public RoomNotAvailableException(String message) {
        super(message);
    }

    public RoomNotAvailableException(int roomId) {
        super("Room " + roomId + " is not available for booking");
    }
}
