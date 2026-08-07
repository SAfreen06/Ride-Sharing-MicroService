package com.example.ride_service.service;

import com.example.ride_service.dto.RideRequestDto;
import com.example.ride_service.dto.RideResponseDto;

public interface RideService {
    RideResponseDto requestRide(RideRequestDto dto);
    RideResponseDto acceptRide(String rideId, String driverId);
    RideResponseDto startRide(String rideId);
    RideResponseDto completeRide(String rideId);
    RideResponseDto cancelRide(String rideId);
    RideResponseDto getRide(String rideId);
}