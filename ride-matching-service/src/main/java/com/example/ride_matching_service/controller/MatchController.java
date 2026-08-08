package com.example.ride_matching_service.controller;

import com.example.ride_matching_service.dto.MatchRequest;
import com.example.ride_matching_service.dto.MatchResult;
import com.example.ride_matching_service.service.MatchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    // Called by the rider's own client (through the gateway) right after
    // POST /api/rides succeeds.
    @PostMapping
    public ResponseEntity<MatchResult> match(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @Valid @RequestBody MatchRequest request) {
        return ResponseEntity.ok(matchService.match(request, authorization));
    }

    // Called by the client after the ride is completed/cancelled, so the
    // driver frees up. Purely local bookkeeping -- no call to ride-service.
    @DeleteMapping("/{rideId}")
    public ResponseEntity<Void> release(@PathVariable String rideId) {
        matchService.release(rideId);
        return ResponseEntity.noContent().build();
    }
}