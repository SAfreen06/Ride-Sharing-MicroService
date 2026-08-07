package com.example.ride_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "driver-service")
public interface DriverClient {

    @GetMapping("/api/drivers/{driverId}/available")
    boolean isDriverAvailable(@PathVariable String driverId);
}
