package com.github.bgvit.notificationservice.notificationservice.repository

import com.github.bgvit.notificationservice.notificationservice.model.Notification
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface NotificationRepository {
    fun getAll(): Flux<Notification>
    fun addNew(notification: Notification): Mono<Void>
    fun getById(id: String): Mono<Notification>
}
