package com.example.fare_service.service;

import com.example.fare_service.dto.FareRequestDto;
import com.example.fare_service.dto.FareResponseDto;

public interface FareService {
    FareResponseDto calculateFare(FareRequestDto request);
    FareResponseDto getFareByRideId(String rideId);
}
