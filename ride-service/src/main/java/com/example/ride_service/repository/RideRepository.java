package com.example.ride_service.repository;

import com.example.ride_service.entity.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RideRepository extends MongoRepository<Ride, String> {
}
