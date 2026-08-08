package com.example.fare_service.service;

import com.example.fare_service.dto.FareRequestDto;
import com.example.fare_service.dto.FareResponseDto;
import com.example.fare_service.entity.Fare;
import com.example.fare_service.exception.FareNotFoundException;
import com.example.fare_service.repository.FareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FareServiceImpl implements FareService {

    private final FareRepository fareRepository;

    private static final double BASE_RATE_PER_KM = 25.0;
    private static final double RATE_PER_MINUTE = 2.0;
    private static final double BASE_FARE = 30.0;

    @Override
    public FareResponseDto calculateFare(FareRequestDto request) {
        double surgeMultiplier = calculateSurge();
        double distanceCost = request.getDistanceKm() * BASE_RATE_PER_KM;
        double timeCost = request.getDurationMinutes() * RATE_PER_MINUTE;
        double total = (BASE_FARE + distanceCost + timeCost) * surgeMultiplier;

        Fare fare = new Fare();
        fare.setRideId(request.getRideId());
        fare.setDistanceKm(request.getDistanceKm());
        fare.setDurationMinutes(request.getDurationMinutes());
        fare.setBaseFare(BASE_FARE);
        fare.setSurgeMultiplier(surgeMultiplier);
        fare.setTotalFare(Math.round(total * 100.0) / 100.0);
        fare.setCalculatedAt(LocalDateTime.now());

        fareRepository.save(fare);

        return new FareResponseDto(fare.getRideId(), fare.getTotalFare(), fare.getSurgeMultiplier());
    }

    @Override
    public FareResponseDto getFareByRideId(String rideId) {
        Fare fare = fareRepository.findByRideId(rideId)
                .orElseThrow(() -> new FareNotFoundException("Fare not found for ride: " + rideId));
        return new FareResponseDto(fare.getRideId(), fare.getTotalFare(), fare.getSurgeMultiplier());
    }

    private double calculateSurge() {
        int hour = LocalDateTime.now().getHour();
        boolean isPeak = (hour >= 8 && hour <= 10) || (hour >= 17 && hour <= 20);
        return isPeak ? 1.5 : 1.0;
    }
}