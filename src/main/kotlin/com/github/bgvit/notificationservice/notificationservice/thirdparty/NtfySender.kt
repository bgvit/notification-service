package com.github.bgvit.notificationservice.notificationservice.thirdparty

import com.github.bgvit.notificationservice.notificationservice.model.NotificationType
import com.github.bgvit.notificationservice.notificationservice.model.dto.NotificationRequest
import com.github.bgvit.notificationservice.notificationservice.service.interfaces.NotificationSender
import com.github.bgvit.notificationservice.notificationservice.thirdparty.response.NtfyResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientException
import org.springframework.web.reactive.function.client.awaitBody
import reactor.core.publisher.Mono

@Component
class NtfySender(
    @Value("\${notification-service.ntfy.url}") val url: String,
    @Value("\${notification-service.ntfy.topic}") val topic: String
) : NotificationSender {

    val webClient: WebClient = WebClient.create("$url$topic")

    override suspend fun send(notificationRequest: NotificationRequest): Boolean {
        return try {
            val response = webClient
                .post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(Mono.just(notificationRequest.text), String::class.java)
                .retrieve()
                .awaitBody<NtfyResponse>()

            response.hasId()
        } catch (t: WebClientException) {
            /*
            TODO(Add log here)
            If the application enter is this exception, it means that the server
            gave us some response with code 4xx or 5xx.
             */
            println(t.localizedMessage)
            false
        } catch (t: Throwable) {
            println(t.localizedMessage)
            /*
            TODO(Add log here)
            Unknown thing happened
            */
            false
        }
    }

    override fun getStrategyName() = NotificationType.PUSH_NTFY
}
