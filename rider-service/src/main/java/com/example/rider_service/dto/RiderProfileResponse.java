package com.example.rider_service.dto;

import com.example.rider_service.entity.RiderProfile;

public class RiderProfileResponse {

    private final String id;
    private final String userId;
    private final String name;
    private final String phone;
    private final String homeAddress;
    private final String preferredPaymentMethod;

    public RiderProfileResponse(RiderProfile profile) {
        this.id = profile.getId();
        this.userId = profile.getUserId();
        this.name = profile.getName();
        this.phone = profile.getPhone();
        this.homeAddress = profile.getHomeAddress();
        this.preferredPaymentMethod = profile.getPreferredPaymentMethod();
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

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getPreferredPaymentMethod() {
        return preferredPaymentMethod;
    }
}
