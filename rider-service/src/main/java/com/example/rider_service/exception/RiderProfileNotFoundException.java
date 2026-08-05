package com.example.rider_service.exception;

public class RiderProfileNotFoundException extends RuntimeException {
    public RiderProfileNotFoundException(String userId) {
        super("No rider profile found for user '" + userId + "'");
    }
}
