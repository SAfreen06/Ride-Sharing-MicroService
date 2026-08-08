package com.example.ride_matching_service.entity;

import java.time.Instant;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

// Tracks which driver is tied up on which ride, purely so this service
// doesn't hand the same driver to two concurrent match requests.
// NOT a copy of ride lifecycle data -- Ride Service owns that.
@Data
@NoArgsConstructor
@Document(collection = "driver_match_records")
public class DriverMatchRecord {

    @Id
    private String id;

    @Indexed(unique = true)
    private String driverId;

    @Indexed(unique = true)
    private String rideId;

    private Instant matchedAt = Instant.now();
}
