package com.example.notification.dto;

import com.example.notification.entity.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationRequest {

    @NotBlank
    private String recipientId;

    private String rideId;

    @NotNull
    private NotificationType type;

    @NotBlank
    private String message;
}
