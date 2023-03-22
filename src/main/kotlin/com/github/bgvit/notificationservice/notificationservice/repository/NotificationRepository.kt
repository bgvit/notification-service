package com.github.bgvit.notificationservice.notificationservice.repository

import com.github.bgvit.notificationservice.notificationservice.model.Notification
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface NotificationRepository {
    suspend fun getById(id: String): Mono<Notification>
    suspend fun save(notification: Notification): Mono<Void>
    suspend fun getAll(): Flux<Notification>
}
