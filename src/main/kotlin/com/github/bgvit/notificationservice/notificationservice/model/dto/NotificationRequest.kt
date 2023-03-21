package com.github.bgvit.notificationservice.notificationservice.model.dto

import com.github.bgvit.notificationservice.notificationservice.model.NotificationType
import java.time.ZonedDateTime

data class NotificationRequest(
    val notificationType: NotificationType,
    val notificationMessage: String,
    val deliveryDate: ZonedDateTime = ZonedDateTime.now(),
    val isReady: Boolean = true
)
