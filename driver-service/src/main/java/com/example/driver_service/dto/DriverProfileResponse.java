package com.example.driver_service.dto;

import com.example.driver_service.entity.DriverProfile;

public class DriverProfileResponse {

    private final String id;
    private final String userId;
    private final String name;
    private final String phone;
    private final String vehicleModel;
    private final String vehiclePlateNumber;
    private final String licenseNumber;
    private final boolean available;

    public DriverProfileResponse(DriverProfile profile) {
        this.id = profile.getId();
        this.userId = profile.getUserId();
        this.name = profile.getName();
        this.phone = profile.getPhone();
        this.vehicleModel = profile.getVehicleModel();
        this.vehiclePlateNumber = profile.getVehiclePlateNumber();
        this.licenseNumber = profile.getLicenseNumber();
        this.available = profile.isAvailable();
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public String getVehiclePlateNumber() {
        return vehiclePlateNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public boolean isAvailable() {
        return available;
    }
}
