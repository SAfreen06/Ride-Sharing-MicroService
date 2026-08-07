package com.example.ride_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "driver-service")
public interface DriverClient {

    @GetMapping("/drivers/{userId}")
    DriverProfileResponse getProfile(@PathVariable String userId);

    @GetMapping("/drivers/available")
    List<DriverProfileResponse> getAvailableDrivers();

    record DriverProfileResponse(String userId, boolean available) {}
}