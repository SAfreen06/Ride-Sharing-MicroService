package com.example.ride_service.service;

import com.example.ride_service.client.FareClient;
import com.example.ride_service.client.DriverClient;
import com.example.ride_service.dto.RideRequestDto;
import com.example.ride_service.dto.RideResponseDto;
import com.example.ride_service.entity.Ride;
import com.example.ride_service.entity.RideStatus;
import com.example.ride_service.exception.RideNotFoundException;
import com.example.ride_service.exception.InvalidRideStateException;
import com.example.ride_service.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final FareClient fareClient;
    private final DriverClient driverClient;

    @Override
    public RideResponseDto requestRide(RideRequestDto dto) {
        Ride ride = new Ride();
        ride.setRiderId(dto.getRiderId());
        ride.setPickupLocation(dto.getPickupLocation());
        ride.setDropLocation(dto.getDropLocation());
        ride.setDistanceKm(dto.getDistanceKm());
        ride.setDurationMinutes(dto.getDurationMinutes());
        ride.setStatus(RideStatus.REQUESTED);
        ride.setCreatedAt(LocalDateTime.now());
        ride.setUpdatedAt(LocalDateTime.now());

        rideRepository.save(ride);
        return toDto(ride);
    }

    @Override
    public RideResponseDto acceptRide(String rideId, String driverId) {
        Ride ride = getRideOrThrow(rideId);
        if (ride.getStatus() != RideStatus.REQUESTED) {
            throw new InvalidRideStateException("Ride is not in REQUESTED state");
        }

        var driver = driverClient.getProfile(driverId);
        if (!driver.available()) {
            throw new InvalidRideStateException("Driver is not available");
        }

        ride.setDriverId(driverId);
        ride.setStatus(RideStatus.ACCEPTED);
        ride.setUpdatedAt(LocalDateTime.now());
        rideRepository.save(ride);
        return toDto(ride);
    }

    @Override
    public RideResponseDto startRide(String rideId) {
        Ride ride = getRideOrThrow(rideId);
        if (ride.getStatus() != RideStatus.ACCEPTED) {
            throw new InvalidRideStateException("Ride is not in ACCEPTED state");
        }
        ride.setStatus(RideStatus.ONGOING);
        ride.setUpdatedAt(LocalDateTime.now());
        rideRepository.save(ride);
        return toDto(ride);
    }

    @Override
    public RideResponseDto completeRide(String rideId) {
        Ride ride = getRideOrThrow(rideId);
        if (ride.getStatus() != RideStatus.ONGOING) {
            throw new InvalidRideStateException("Ride is not in ONGOING state");
        }

        var fareResponse = fareClient.calculate(
                new FareClient.FareRequest(rideId, ride.getDistanceKm(), ride.getDurationMinutes())
        );

        ride.setFare(fareResponse.totalFare());
        ride.setStatus(RideStatus.COMPLETED);
        ride.setUpdatedAt(LocalDateTime.now());
        rideRepository.save(ride);
        return toDto(ride);
    }

    @Override
    public RideResponseDto cancelRide(String rideId) {
        Ride ride = getRideOrThrow(rideId);
        if (ride.getStatus() == RideStatus.COMPLETED) {
            throw new InvalidRideStateException("Cannot cancel a completed ride");
        }
        ride.setStatus(RideStatus.CANCELLED);
        ride.setUpdatedAt(LocalDateTime.now());
        rideRepository.save(ride);
        return toDto(ride);
    }

    @Override
    public RideResponseDto getRide(String rideId) {
        return toDto(getRideOrThrow(rideId));
    }

    private Ride getRideOrThrow(String rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new RideNotFoundException("Ride not found: " + rideId));
    }

    private RideResponseDto toDto(Ride ride) {
        return RideResponseDto.builder()
                .id(ride.getId())
                .riderId(ride.getRiderId())
                .driverId(ride.getDriverId())
                .pickupLocation(ride.getPickupLocation())
                .dropLocation(ride.getDropLocation())
                .fare(ride.getFare())
                .status(ride.getStatus())
                .build();
    }
}