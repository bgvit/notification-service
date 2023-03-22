package com.github.bgvit.notificationservice.notificationservice.model.dto

import java.time.ZonedDateTime

data class NotificationRequest(
    val accountId: String,
    val notificationType: String,
    val notificationMessage: String,
    val deliveryDate: String = ZonedDateTime.now().toString(),
    val isReady: Boolean = true
)
