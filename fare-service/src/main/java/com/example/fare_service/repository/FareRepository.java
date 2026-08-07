package com.example.fare_service.repository;

import com.example.fare_service.entity.Fare;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface FareRepository extends MongoRepository<Fare, String> {
    Optional<Fare> findByRideId(String rideId);
}