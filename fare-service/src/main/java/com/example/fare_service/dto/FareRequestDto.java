package com.example.fare_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter @Setter
public class FareRequestDto {
    @NotBlank
    private String rideId;
    @Positive
    private double distanceKm;
    @Positive
    private double durationMinutes;
}