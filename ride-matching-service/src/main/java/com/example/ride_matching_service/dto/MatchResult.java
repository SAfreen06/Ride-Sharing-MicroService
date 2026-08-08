package com.example.ride_matching_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MatchResult {
    private String rideId;
    private String driverId;
    private boolean matched;
}