package com.example.rider_service.controller;

import com.example.rider_service.dto.RiderProfileRequest;
import com.example.rider_service.dto.RiderProfileResponse;
import com.example.rider_service.service.RiderService;
import jakarta.validation.Valid;
import java.security.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/riders")
public class RiderController {

    private final RiderService riderService;

    public RiderController(RiderService riderService) {
        this.riderService = riderService;
    }

    @PostMapping
    public ResponseEntity<RiderProfileResponse> createProfile(Principal principal,
                                                                @Valid @RequestBody RiderProfileRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(riderService.createProfile(principal.getName(), request));
    }

    @GetMapping("/me")
    public ResponseEntity<RiderProfileResponse> getMyProfile(Principal principal) {
        return ResponseEntity.ok(riderService.getProfile(principal.getName()));
    }

    @PutMapping("/me")
    public ResponseEntity<RiderProfileResponse> updateMyProfile(Principal principal,
                                                                  @Valid @RequestBody RiderProfileRequest request) {
        return ResponseEntity.ok(riderService.updateProfile(principal.getName(), request));
    }

    // Looked up by other services (e.g. Ride Service, Notification Service)
    // that only have the rider's userId, not their own auth context.
    @GetMapping("/{userId}")
    public ResponseEntity<RiderProfileResponse> getProfileByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(riderService.getProfile(userId));
    }
}
