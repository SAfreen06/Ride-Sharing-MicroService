// DriverClient.java
package com.example.ride_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "driver-service")
public interface DriverClient {

    @GetMapping("/drivers/available")
    List<DriverProfileResponse> getAvailableDrivers();

    record DriverProfileResponse(String userId, boolean available) {}
}