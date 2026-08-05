package com.example.driver_service.service;

import com.example.driver_service.dto.DriverProfileRequest;
import com.example.driver_service.dto.DriverProfileResponse;
import com.example.driver_service.entity.DriverProfile;
import com.example.driver_service.exception.DriverProfileAlreadyExistsException;
import com.example.driver_service.exception.DriverProfileNotFoundException;
import com.example.driver_service.repository.DriverProfileRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    private final DriverProfileRepository driverProfileRepository;

    public DriverService(DriverProfileRepository driverProfileRepository) {
        this.driverProfileRepository = driverProfileRepository;
    }

    public DriverProfileResponse createProfile(String userId, DriverProfileRequest request) {
        if (driverProfileRepository.existsByUserId(userId)) {
            throw new DriverProfileAlreadyExistsException(userId);
        }

        DriverProfile profile = new DriverProfile();
        profile.setUserId(userId);
        applyRequest(profile, request);
        profile = driverProfileRepository.save(profile);

        return new DriverProfileResponse(profile);
    }

    public DriverProfileResponse getProfile(String userId) {
        DriverProfile profile = driverProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new DriverProfileNotFoundException(userId));
        return new DriverProfileResponse(profile);
    }

    public DriverProfileResponse updateProfile(String userId, DriverProfileRequest request) {
        DriverProfile profile = driverProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new DriverProfileNotFoundException(userId));
        applyRequest(profile, request);
        profile = driverProfileRepository.save(profile);

        return new DriverProfileResponse(profile);
    }

    public DriverProfileResponse setAvailability(String userId, boolean available) {
        DriverProfile profile = driverProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new DriverProfileNotFoundException(userId));
        profile.setAvailable(available);
        profile = driverProfileRepository.save(profile);

        return new DriverProfileResponse(profile);
    }

    public List<DriverProfileResponse> getAvailableDrivers() {
        return driverProfileRepository.findByAvailableTrue().stream()
                .map(DriverProfileResponse::new)
                .toList();
    }

    private void applyRequest(DriverProfile profile, DriverProfileRequest request) {
        profile.setName(request.getName());
        profile.setPhone(request.getPhone());
        profile.setVehicleModel(request.getVehicleModel());
        profile.setVehiclePlateNumber(request.getVehiclePlateNumber());
        profile.setLicenseNumber(request.getLicenseNumber());
    }
}
