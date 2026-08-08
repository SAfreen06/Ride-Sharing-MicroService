package com.example.ride_matching_service.service;

import com.example.ride_matching_service.client.DriverAvailabilityDto;
import com.example.ride_matching_service.client.DriverServiceClient;
import com.example.ride_matching_service.client.RideServiceClient;
import com.example.ride_matching_service.dto.MatchRequest;
import com.example.ride_matching_service.dto.MatchResult;
import com.example.ride_matching_service.entity.DriverMatchRecord;
import com.example.ride_matching_service.repository.DriverMatchRecordRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    private final DriverServiceClient driverServiceClient;
    private final RideServiceClient rideServiceClient;
    private final DriverMatchRecordRepository driverMatchRecordRepository;

    public MatchService(DriverServiceClient driverServiceClient,
                        RideServiceClient rideServiceClient,
                        DriverMatchRecordRepository driverMatchRecordRepository) {
        this.driverServiceClient = driverServiceClient;
        this.rideServiceClient = rideServiceClient;
        this.driverMatchRecordRepository = driverMatchRecordRepository;
    }

    public MatchResult match(MatchRequest request) {
        List<DriverAvailabilityDto> availableDrivers = driverServiceClient.getAvailableDrivers();

        Optional<String> matchedDriverId = availableDrivers.stream()
                .map(DriverAvailabilityDto::getUserId)
                .filter(driverId -> !driverMatchRecordRepository.existsByDriverId(driverId))
                .findFirst();

        MatchResult result;
        if (matchedDriverId.isPresent()) {
            DriverMatchRecord record = new DriverMatchRecord();
            record.setDriverId(matchedDriverId.get());
            record.setRideId(request.getRideId());
            driverMatchRecordRepository.save(record);
            result = new MatchResult(request.getRideId(), matchedDriverId.get(), true);
        } else {
            result = new MatchResult(request.getRideId(), null, false);
        }

        rideServiceClient.reportMatchResult(result);
        return result;
    }

    // Called once Ride Service marks the ride completed/cancelled, so the
    // driver becomes matchable again.
    public void release(String rideId) {
        driverMatchRecordRepository.deleteByRideId(rideId);
    }
}