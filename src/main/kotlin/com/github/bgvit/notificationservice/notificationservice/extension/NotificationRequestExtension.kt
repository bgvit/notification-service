package com.github.bgvit.notificationservice.notificationservice.extension

import com.github.bgvit.notificationservice.notificationservice.model.Notification
import com.github.bgvit.notificationservice.notificationservice.model.NotificationType
import com.github.bgvit.notificationservice.notificationservice.model.dto.NotificationRequest
import com.github.ksuid.Ksuid
import java.time.ZonedDateTime
import java.time.format.DateTimeParseException

fun NotificationRequest.toNotificationEntity(notificationType: NotificationType, wasSent: Boolean) =
    Notification(
        id = Ksuid.newKsuid().toString(),
        notificationType = notificationType,
        notificationMessage = this.text,
        deliveryDate = this.deliveryDate.toZonedDateTime(),
        accountId = this.accountId,
        isReady = this.isReady,
        wasSent = wasSent
    )

fun String?.toZonedDateTime(): ZonedDateTime {
    try {
        if (this == null) {
            return ZonedDateTime.now()
        }
        return ZonedDateTime.parse(this)
    } catch (t: DateTimeParseException) {
        return ZonedDateTime.now()
    }
}
