package com.example.ride_matching_service.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class RideServiceClient {

    private final RestClient restClient;

    public RideServiceClient(@Qualifier("loadBalanced") RestClient.Builder loadBalancedRestClientBuilder) {
        this.restClient = loadBalancedRestClientBuilder.baseUrl("http://ride-service").build();
    }

    // ride-service requires a valid JWT on every /rides/** call, and it
    // re-validates driver availability itself before accepting. We forward
    // the rider's own token (the one that hit our /matches endpoint) rather
    // than trying to authenticate as a service -- ride-matching-service has
    // no identity of its own to present.
    public boolean acceptRide(String rideId, String driverId, String authorizationHeader) {
        try {
            restClient.put()
                    .uri(uriBuilder -> uriBuilder.path("/rides/{rideId}/accept")
                            .queryParam("driverId", driverId)
                            .build(rideId))
                    .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                    .retrieve()
                    .toBodilessEntity();
            return true;
        } catch (RestClientException e) {
            // ride-service rejected it (ride not REQUESTED anymore, driver no
            // longer available by the time it double-checked, or it's just
            // unreachable) -- caller is responsible for releasing the local hold.
            return false;
        }
    }
}