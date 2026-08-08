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

    public MatchResult match(MatchRequest request, String authorizationHeader) {
        List<DriverAvailabilityDto> availableDrivers = driverServiceClient.getAvailableDrivers();

        Optional<String> matchedDriverId = availableDrivers.stream()
                .map(DriverAvailabilityDto::getUserId)
                .filter(driverId -> !driverMatchRecordRepository.existsByDriverId(driverId))
                .findFirst();

        if (matchedDriverId.isEmpty()) {
            return new MatchResult(request.getRideId(), null, false);
        }

        String driverId = matchedDriverId.get();

        DriverMatchRecord record = new DriverMatchRecord();
        record.setDriverId(driverId);
        record.setRideId(request.getRideId());
        driverMatchRecordRepository.save(record);

        boolean accepted = rideServiceClient.acceptRide(request.getRideId(), driverId, authorizationHeader);

        if (!accepted) {
            // ride-service didn't confirm the assignment -- don't leave this
            // driver stuck "busy" in our own records for nothing.
            driverMatchRecordRepository.deleteByRideId(request.getRideId());
            return new MatchResult(request.getRideId(), null, false);
        }

        return new MatchResult(request.getRideId(), driverId, true);
    }

    public void release(String rideId) {
        driverMatchRecordRepository.deleteByRideId(rideId);
    }
}