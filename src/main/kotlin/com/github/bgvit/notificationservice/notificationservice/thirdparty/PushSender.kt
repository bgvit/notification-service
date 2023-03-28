package com.github.bgvit.notificationservice.notificationservice.thirdparty

import com.github.bgvit.notificationservice.notificationservice.model.NotificationType
import com.github.bgvit.notificationservice.notificationservice.model.dto.NotificationRequest
import com.github.bgvit.notificationservice.notificationservice.service.interfaces.NotificationSender
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientException
import reactor.core.publisher.Mono

@Component
class PushSender(
    @Value("\${notification-service.push.url}") val url: String
) : NotificationSender {

    companion object val webClient: WebClient = WebClient.create("$url")

    override suspend fun send(notification: NotificationRequest): Boolean {
        return try {
            webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(notification.text), String::class.java)
                .retrieve()

            true
        } catch (t: WebClientException) {
            /*
            If the application enter is this exception, it means that the server
            gave us some response with code 4xx or 5xx.
             */
            t.localizedMessage
            false
        } catch (t: Throwable) {
            /* Unknown thing happened */
            false
        }
    }

    override fun getStrategyName(): NotificationType = NotificationType.PUSH
}
