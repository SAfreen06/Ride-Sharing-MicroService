package com.example.ride_matching_service.repository;


import com.example.ride_matching_service.entity.DriverMatchRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DriverMatchRecordRepository extends MongoRepository<DriverMatchRecord, String> {
    boolean existsByDriverId(String driverId);
    void deleteByRideId(String rideId);
}
