package com.example.ride_matching_service.client;

import lombok.Data;

@Data
public class DriverAvailabilityDto {
    private String id;
    private String userId;
    private String name;
    private String phone;
    private String vehicleModel;
    private String vehiclePlateNumber;
    private boolean available;
}