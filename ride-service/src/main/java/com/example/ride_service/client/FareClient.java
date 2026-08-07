package com.example.ride_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "fare-service")
public interface FareClient {

    @PostMapping("/fares/calculate")
    FareResponse calculate(@RequestBody FareRequest request);

    record FareRequest(String rideId, double distanceKm, double durationMinutes) {}
    record FareResponse(String rideId, double totalFare, double surgeMultiplier) {}
}