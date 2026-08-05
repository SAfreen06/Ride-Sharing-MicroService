package com.example.driver_service.exception;

public class DriverProfileAlreadyExistsException extends RuntimeException {
    public DriverProfileAlreadyExistsException(String userId) {
        super("A driver profile already exists for user '" + userId + "'");
    }
}
