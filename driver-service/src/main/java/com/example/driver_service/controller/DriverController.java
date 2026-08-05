package com.example.driver_service.controller;

import com.example.driver_service.dto.AvailabilityRequest;
import com.example.driver_service.dto.DriverProfileRequest;
import com.example.driver_service.dto.DriverProfileResponse;
import com.example.driver_service.service.DriverService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public ResponseEntity<DriverProfileResponse> createProfile(Principal principal,
                                                                 @Valid @RequestBody DriverProfileRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(driverService.createProfile(principal.getName(), request));
    }

    @GetMapping("/me")
    public ResponseEntity<DriverProfileResponse> getMyProfile(Principal principal) {
        return ResponseEntity.ok(driverService.getProfile(principal.getName()));
    }

    @PutMapping("/me")
    public ResponseEntity<DriverProfileResponse> updateMyProfile(Principal principal,
                                                                   @Valid @RequestBody DriverProfileRequest request) {
        return ResponseEntity.ok(driverService.updateProfile(principal.getName(), request));
    }

    @PatchMapping("/me/availability")
    public ResponseEntity<DriverProfileResponse> setAvailability(Principal principal,
                                                                   @Valid @RequestBody AvailabilityRequest request) {
        return ResponseEntity.ok(driverService.setAvailability(principal.getName(), request.getAvailable()));
    }

    // Looked up by other services (e.g. Ride Service, Notification Service)
    // that only have the driver's userId, not their own auth context.
    @GetMapping("/{userId}")
    public ResponseEntity<DriverProfileResponse> getProfileByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(driverService.getProfile(userId));
    }

    // The prerequisite input for the Ride Matching Service: which drivers
    // are currently available to be matched with a pending ride request.
    @GetMapping("/available")
    public ResponseEntity<List<DriverProfileResponse>> getAvailableDrivers() {
        return ResponseEntity.ok(driverService.getAvailableDrivers());
    }
}
