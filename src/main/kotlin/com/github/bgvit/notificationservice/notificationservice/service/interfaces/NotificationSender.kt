package com.github.bgvit.notificationservice.notificationservice.service.interfaces

import com.github.bgvit.notificationservice.notificationservice.model.NotificationType
import com.github.bgvit.notificationservice.notificationservice.model.dto.NotificationRequest

interface NotificationSender : StrategyBase<NotificationType> {
    suspend fun send(notificationRequest: NotificationRequest): Boolean
}
