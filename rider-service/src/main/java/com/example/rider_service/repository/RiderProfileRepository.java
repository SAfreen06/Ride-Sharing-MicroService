package com.example.rider_service.repository;

import com.example.rider_service.entity.RiderProfile;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RiderProfileRepository extends MongoRepository<RiderProfile, String> {
    Optional<RiderProfile> findByUserId(String userId);
    boolean existsByUserId(String userId);
}
