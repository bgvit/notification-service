package com.github.bgvit.notificationservice.notificationservice.model.dto

import java.time.ZonedDateTime

data class NotificationRequest(
    val id: String? = null,
    val accountId: String,
    val notificationType: String,
    val text: String,
    val deliveryDate: String = ZonedDateTime.now().toString(),
    val isReady: Boolean = true,
    val wasSent: Boolean?
)
