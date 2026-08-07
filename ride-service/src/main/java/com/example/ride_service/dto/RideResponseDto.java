package com.example.ride_service.dto;

import com.example.ride_service.entity.RideStatus;
import lombok.*;

@Getter @Setter @Builder
public class RideResponseDto {
    private String id;
    private String riderId;
    private String driverId;
    private String pickupLocation;
    private String dropLocation;
    private double fare;
    private RideStatus status;
}
