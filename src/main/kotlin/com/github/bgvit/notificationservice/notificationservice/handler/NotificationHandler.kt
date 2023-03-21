package com.github.bgvit.notificationservice.notificationservice.handler

import com.github.bgvit.notificationservice.notificationservice.model.dto.NotificationRequest
import com.github.bgvit.notificationservice.notificationservice.service.NotificationService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.json

@Component
class NotificationHandler(private val notificationService: NotificationService) {

    suspend fun addNewNotification(request: ServerRequest): ServerResponse {
        val notification = request.awaitBody<NotificationRequest>()

        notificationService.addNewNotification(notification)

        return ServerResponse
            .ok()
            .json()
            .bodyValueAndAwait(Any::class)
    }
}
