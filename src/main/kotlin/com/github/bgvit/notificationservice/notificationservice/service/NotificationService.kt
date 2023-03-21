package com.github.bgvit.notificationservice.notificationservice.service

import com.github.bgvit.notificationservice.notificationservice.model.dto.NotificationRequest
import org.springframework.stereotype.Service

@Service
class NotificationService(val senders: List<NotificationSender>) {

    suspend fun addNewNotification(notification: NotificationRequest) {
        val wasSent = senders.forEach {
            if (it.shouldSend(notification)) {
                it.send(notification)
            }
        }
    }
}
