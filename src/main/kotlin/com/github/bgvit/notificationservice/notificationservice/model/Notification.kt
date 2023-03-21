package com.github.bgvit.notificationservice.notificationservice.model

import org.springframework.data.annotation.Id
import java.time.ZonedDateTime

data class Notification(
    @Id
    val id: String,
    val notificationType: NotificationType,
    val notificationMessage: String,
    val deliveryDate: ZonedDateTime,
    val accountId: String,
    val isReady: Boolean?,
    val wasSent: Boolean?
)

enum class NotificationType {
    PUSH, SMS
}
