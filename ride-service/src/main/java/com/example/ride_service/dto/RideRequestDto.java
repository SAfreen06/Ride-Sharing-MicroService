package com.example.ride_service.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter @Setter
public class RideRequestDto {
    @NotBlank
    private String riderId;
    @NotBlank
    private String pickupLocation;
    @NotBlank
    private String dropLocation;
    @Positive
    private double distanceKm;
    @Positive
    private double durationMinutes;
}
