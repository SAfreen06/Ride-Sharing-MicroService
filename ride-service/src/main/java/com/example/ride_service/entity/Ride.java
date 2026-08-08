package com.example.ride_service.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "rides")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Ride {

    @Id
    private String id;

    private String riderId;
    private String driverId;
    private String pickupLocation;
    private String dropLocation;
    private double distanceKm;
    private double durationMinutes;
    private double fare;
    private RideStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
