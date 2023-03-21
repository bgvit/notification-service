package com.github.bgvit.notificationservice.notificationservice.model

import org.springframework.data.annotation.Id
import java.time.ZonedDateTime

data class Notification(
    @Id
    val id: String,
    val notificationType: NotificationType,
    val notificationMessage: String,
    val deliveryDate: ZonedDateTime = ZonedDateTime.now(),
    val accountId: String,
    val isReady: Boolean = true,
    val wasSent: Boolean?
)
