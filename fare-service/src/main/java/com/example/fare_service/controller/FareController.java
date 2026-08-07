package com.example.fare_service.controller;

import com.example.fare_service.dto.FareRequestDto;
import com.example.fare_service.dto.FareResponseDto;
import com.example.fare_service.service.FareService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fares")
@RequiredArgsConstructor
public class FareController {

    private final FareService fareService;

    @PostMapping("/calculate")
    public ResponseEntity<FareResponseDto> calculate(@Valid @RequestBody FareRequestDto request) {
        return ResponseEntity.ok(fareService.calculateFare(request));
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<FareResponseDto> getByRideId(@PathVariable String rideId) {
        return ResponseEntity.ok(fareService.getFareByRideId(rideId));
    }
}