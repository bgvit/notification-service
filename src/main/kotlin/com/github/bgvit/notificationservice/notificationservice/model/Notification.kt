package com.github.bgvit.notificationservice.notificationservice.model

import com.github.ksuid.KsuidGenerator
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.ZonedDateTime

@Table("notification")
data class Notification(
    @Id
    val id: String = KsuidGenerator.generate().toString(),
    val notificationType: NotificationType,
    val notificationMessage: String,
    val deliveryDate: ZonedDateTime = ZonedDateTime.now(),
    val accountId: String,
    val isReady: Boolean = true,
    val wasSent: Boolean = false
)
