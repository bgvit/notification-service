package com.github.bgvit.notificationservice.notificationservice.service

import com.github.bgvit.notificationservice.notificationservice.model.dto.NotificationRequest

interface NotificationSender {
    suspend fun shouldSend(notification: NotificationRequest): Boolean
    suspend fun send(notification: NotificationRequest) : Boolean
}
