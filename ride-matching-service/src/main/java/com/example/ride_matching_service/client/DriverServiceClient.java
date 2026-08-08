package com.example.ride_matching_service.client;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class DriverServiceClient {

    private final RestClient restClient;

    public DriverServiceClient(@Qualifier("loadBalanced") RestClient.Builder loadBalancedRestClientBuilder) {
        this.restClient = loadBalancedRestClientBuilder.baseUrl("http://driver-service").build();
    }

    // GET /drivers/available is intentionally public on driver-service --
    // we have no rider/driver JWT of our own to present here.
    public List<DriverAvailabilityDto> getAvailableDrivers() {
        try {
            DriverAvailabilityDto[] drivers = restClient.get()
                    .uri("/drivers/available")
                    .retrieve()
                    .body(DriverAvailabilityDto[].class);
            return drivers == null ? List.of() : List.of(drivers);
        } catch (RestClientException e) {
            // driver-service down/unreachable -> treat as "no drivers available"
            // rather than failing the match request outright.
            return List.of();
        }
    }
}