package com.example.driver_service.exception;

public class DriverProfileNotFoundException extends RuntimeException {
    public DriverProfileNotFoundException(String userId) {
        super("No driver profile found for user '" + userId + "'");
    }
}
