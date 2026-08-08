package com.example.fare_service.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "fares")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Fare {

    @Id
    private String id;

    private String rideId;
    private double distanceKm;
    private double durationMinutes;
    private double baseFare;
    private double surgeMultiplier;
    private double totalFare;
    private LocalDateTime calculatedAt;
}