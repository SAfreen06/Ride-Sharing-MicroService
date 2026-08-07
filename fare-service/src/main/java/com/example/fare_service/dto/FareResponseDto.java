package com.example.fare_service.dto;

import lombok.*;

@Getter @Setter @AllArgsConstructor
public class FareResponseDto {
    private String rideId;
    private double totalFare;
    private double surgeMultiplier;
}
