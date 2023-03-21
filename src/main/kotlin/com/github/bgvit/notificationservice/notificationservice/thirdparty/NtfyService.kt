package com.github.bgvit.notificationservice.notificationservice.thirdparty

import com.github.bgvit.notificationservice.notificationservice.model.NotificationType
import com.github.bgvit.notificationservice.notificationservice.model.dto.NotificationRequest
import com.github.bgvit.notificationservice.notificationservice.service.NotificationSender
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientException
import java.nio.charset.StandardCharsets
import java.time.ZonedDateTime

@Component
class NtfyService(
    @Value("\${notification-service.ntfy.url}") val url: String,
    @Value("\${notification-service.ntfy.topic}") val topic: String
) : NotificationSender {
    companion object {
        val webClient: WebClient = WebClient.builder().baseUrl("$this.url/$this.topic").build()
    }
    override suspend fun shouldSend(notification: NotificationRequest): Boolean {
        return isContainedOnAllowedTypes(notification.notificationType)
    }

    override suspend fun send(notification: NotificationRequest): Boolean {
        return try {
            PushService.webClient.post()
                .bodyValue(notification)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .acceptCharset(StandardCharsets.UTF_8)
                .ifNoneMatch("*")
                .ifModifiedSince(ZonedDateTime.now())
                .retrieve()

            true
        } catch (t: WebClientException) {
            /*
            TODO(Add log here)
            If the application enter is this exception, it means that the server
            gave us some response with code 4xx or 5xx.
             */
            t.localizedMessage
            false
        } catch (t: Throwable) {
            /*
            TODO(Add log here)
            Unknown thing happened
            */
            false
        }
    }

    private fun isContainedOnAllowedTypes(requestType: NotificationType) =
        requestType.equals(NotificationType.PUSH_NTFY)
}
