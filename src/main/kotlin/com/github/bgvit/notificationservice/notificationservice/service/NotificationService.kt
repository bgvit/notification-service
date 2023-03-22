package com.github.bgvit.notificationservice.notificationservice.service

import com.github.bgvit.notificationservice.notificationservice.extension.toNotificationEntity
import com.github.bgvit.notificationservice.notificationservice.model.NotificationType
import com.github.bgvit.notificationservice.notificationservice.model.dto.NotificationRequest
import com.github.bgvit.notificationservice.notificationservice.repository.NotificationRepository
import com.github.bgvit.notificationservice.notificationservice.repository.impl.NotificationRequestCacheRepository
import com.github.bgvit.notificationservice.notificationservice.service.factory.NotificationSenderFactory
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class NotificationService(
    val notificationSenderFactory: NotificationSenderFactory,
    val notificationRepository: NotificationRepository,
    val notificationRequestCacheRepository: NotificationRequestCacheRepository
) {

    suspend fun process(notificationRequest: NotificationRequest): ResponseEntity<String> {
        try {
            val isDuplicated = isDuplicated(notificationRequest)
            val notificationType = NotificationType.valueOf(notificationRequest.notificationType.uppercase())

            if (isDuplicated.await()) {
                return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Duplicated message, please wait 2 minutes to send or change the text.")
            }

            val notificationSender = notificationSenderFactory.findStrategy(notificationType)
                ?: return ResponseEntity.unprocessableEntity().body("Wrong NotificationType. Try again changing it.")

            val wasSent = notificationSender.send(notificationRequest)

            persistNotification(notificationRequest, notificationType, wasSent).apply {
                if (this) {
                    notificationRequestCacheRepository.addNewNotificationRequest(notificationRequest)
                }
            }

            return ResponseEntity.ok("Success")
        } catch (t: Throwable) {
            println(t.localizedMessage)
            return ResponseEntity.internalServerError().body("Some internal problem occurred, please try again.")
        }
    }

    private suspend fun persistNotification(notificationRequest: NotificationRequest, notificationType: NotificationType, wasSent: Boolean): Boolean {
        return try {
            notificationRepository.save(notificationRequest.toNotificationEntity(notificationType, wasSent)).awaitSingleOrNull()
            true
        } catch (t: Throwable) {
            println(t.localizedMessage)
            /*TODO("Implement way to assure the persistency of the notification in case of error")*/
            false
        }
    }

    private suspend fun isDuplicated(notificationRequest: NotificationRequest): Deferred<Boolean> {
        return notificationRequestCacheRepository.wasAlreadySent(notificationRequest)
    }
}
