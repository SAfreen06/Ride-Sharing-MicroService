package com.example.notification.service;

import com.example.notification.dto.NotificationRequest;
import com.example.notification.dto.NotificationResponse;
import com.example.notification.entity.Notification;
import com.example.notification.repository.NotificationRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public NotificationResponse send(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setRecipientId(request.getRecipientId());
        notification.setRideId(request.getRideId());
        notification.setType(request.getType());
        notification.setMessage(request.getMessage());
        notification = notificationRepository.save(notification);

        // Simulated send -- per the proposal, a log/console stub is the whole
        // point here, not a real SMS/email/push integration.
        log.info("[SIMULATED NOTIFICATION] To: {} | Type: {} | Message: {}",
                notification.getRecipientId(), notification.getType(), notification.getMessage());

        return new NotificationResponse(notification);
    }

    public List<NotificationResponse> getMyNotifications(String recipientId) {
        return notificationRepository.findByRecipientIdOrderBySentAtDesc(recipientId).stream()
                .map(NotificationResponse::new)
                .toList();
    }
}
