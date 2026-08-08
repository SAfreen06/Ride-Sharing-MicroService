package com.example.ride_matching_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MatchRequest {

    @NotBlank
    private String rideId;
}
