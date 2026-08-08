package com.example.ride_matching_service.client;

import com.example.ride_matching_service.dto.MatchResult;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

// ASSUMED CONTRACT -- ride-service isn't built yet. Once it is, confirm with
// your teammate: (1) the Eureka application name if not "ride-service",
// (2) the actual endpoint path/method, (3) the expected body shape.
// Everything else in this service is independent of this guess.
@Component
public class RideServiceClient {

    private final RestClient restClient;

    public RideServiceClient(@Qualifier("loadBalanced") RestClient.Builder loadBalancedRestClientBuilder) {
        this.restClient = loadBalancedRestClientBuilder.baseUrl("http://ride-service").build();
    }

    public void reportMatchResult(MatchResult result) {
        try {
            restClient.patch()
                    .uri("/rides/{rideId}/match-result", result.getRideId())
                    .body(result)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException e) {
            // ride-service isn't up/finished yet -- don't fail the match
            // because of that. The caller still gets the result synchronously.
        }
    }
}