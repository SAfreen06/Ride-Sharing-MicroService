package com.example.notification.dto;

import com.example.notification.entity.Notification;
import com.example.notification.entity.NotificationType;
import java.time.Instant;
import lombok.Getter;

@Getter
public class NotificationResponse {

    private final String id;
    private final String rideId;
    private final NotificationType type;
    private final String message;
    private final Instant sentAt;

    public NotificationResponse(Notification notification) {
        this.id = notification.getId();
        this.rideId = notification.getRideId();
        this.type = notification.getType();
        this.message = notification.getMessage();
        this.sentAt = notification.getSentAt();
    }
}
