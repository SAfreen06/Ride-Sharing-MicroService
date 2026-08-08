package com.example.notification.repository;

import com.example.notification.entity.Notification;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByRecipientIdOrderBySentAtDesc(String recipientId);
}
