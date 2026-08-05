package com.example.rider_service.exception;

public class RiderProfileAlreadyExistsException extends RuntimeException {
    public RiderProfileAlreadyExistsException(String userId) {
        super("A rider profile already exists for user '" + userId + "'");
    }
}
