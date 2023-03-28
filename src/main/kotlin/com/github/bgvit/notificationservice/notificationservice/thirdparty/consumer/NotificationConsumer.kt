package com.github.bgvit.notificationservice.notificationservice.thirdparty.consumer

import com.github.bgvit.notificationservice.notificationservice.model.dto.NotificationRequest
import com.github.bgvit.notificationservice.notificationservice.service.NotificationService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class NotificationConsumer(
    val notificationService: NotificationService
) {
    @KafkaListener(
        topics = ["\${notification-service.kafka.consumer.topic}"],
        groupId = "\${notification-service.kafka.consumer.group-id"
    )
    suspend fun process(message: NotificationRequest) {
        notificationService.process(message)
    }
}
