package com.example.ride_service.controller;

import com.example.ride_service.dto.RideRequestDto;
import com.example.ride_service.dto.RideResponseDto;
import com.example.ride_service.service.RideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping
    public ResponseEntity<RideResponseDto> requestRide(@Valid @RequestBody RideRequestDto dto) {
        return ResponseEntity.ok(rideService.requestRide(dto));
    }

    @PutMapping("/{rideId}/accept")
    public ResponseEntity<RideResponseDto> accept(@PathVariable String rideId, @RequestParam String driverId) {
        return ResponseEntity.ok(rideService.acceptRide(rideId, driverId));
    }

    @PutMapping("/{rideId}/start")
    public ResponseEntity<RideResponseDto> start(@PathVariable String rideId) {
        return ResponseEntity.ok(rideService.startRide(rideId));
    }

    @PutMapping("/{rideId}/complete")
    public ResponseEntity<RideResponseDto> complete(@PathVariable String rideId) {
        return ResponseEntity.ok(rideService.completeRide(rideId));
    }

    @PutMapping("/{rideId}/cancel")
    public ResponseEntity<RideResponseDto> cancel(@PathVariable String rideId) {
        return ResponseEntity.ok(rideService.cancelRide(rideId));
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<RideResponseDto> get(@PathVariable String rideId) {
        return ResponseEntity.ok(rideService.getRide(rideId));
    }
}
