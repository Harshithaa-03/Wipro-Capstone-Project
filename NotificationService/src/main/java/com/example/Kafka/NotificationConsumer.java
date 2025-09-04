package com.example.Kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.Entity.Notification;
import com.example.Service.NotificationService;

@Component
public class NotificationConsumer {

	
	private final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "notification-topics", groupId = "notification-group")
    public void listen(NotificationEvent event) {
        System.out.println("Received Kafka event: " + event);

        Notification notification = Notification.builder()
                .email(event.getEmail())
                .otp(event.getMessage())
                .build();

        notificationService.saveNotification(notification);
    }
}
