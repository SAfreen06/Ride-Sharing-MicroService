package com.example.driver_service.repository;

import com.example.driver_service.entity.DriverProfile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DriverProfileRepository extends MongoRepository<DriverProfile, String> {
    Optional<DriverProfile> findByUserId(String userId);
    boolean existsByUserId(String userId);
    List<DriverProfile> findByAvailableTrue();
}
