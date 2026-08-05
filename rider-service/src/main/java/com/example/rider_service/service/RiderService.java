package com.example.rider_service.service;

import com.example.rider_service.dto.RiderProfileRequest;
import com.example.rider_service.dto.RiderProfileResponse;
import com.example.rider_service.entity.RiderProfile;
import com.example.rider_service.exception.RiderProfileAlreadyExistsException;
import com.example.rider_service.exception.RiderProfileNotFoundException;
import com.example.rider_service.repository.RiderProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class RiderService {

    private final RiderProfileRepository riderProfileRepository;

    public RiderService(RiderProfileRepository riderProfileRepository) {
        this.riderProfileRepository = riderProfileRepository;
    }

    public RiderProfileResponse createProfile(String userId, RiderProfileRequest request) {
        if (riderProfileRepository.existsByUserId(userId)) {
            throw new RiderProfileAlreadyExistsException(userId);
        }

        RiderProfile profile = new RiderProfile();
        profile.setUserId(userId);
        applyRequest(profile, request);
        profile = riderProfileRepository.save(profile);

        return new RiderProfileResponse(profile);
    }

    public RiderProfileResponse getProfile(String userId) {
        RiderProfile profile = riderProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RiderProfileNotFoundException(userId));
        return new RiderProfileResponse(profile);
    }

    public RiderProfileResponse updateProfile(String userId, RiderProfileRequest request) {
        RiderProfile profile = riderProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RiderProfileNotFoundException(userId));
        applyRequest(profile, request);
        profile = riderProfileRepository.save(profile);

        return new RiderProfileResponse(profile);
    }

    private void applyRequest(RiderProfile profile, RiderProfileRequest request) {
        profile.setName(request.getName());
        profile.setPhone(request.getPhone());
        profile.setHomeAddress(request.getHomeAddress());
        profile.setPreferredPaymentMethod(request.getPreferredPaymentMethod());
    }
}
