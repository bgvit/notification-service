package com.github.bgvit.notificationservice.notificationservice.repository.impl

import com.github.bgvit.notificationservice.notificationservice.model.dto.NotificationRequest
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.ReactiveValueOperations
import org.springframework.data.redis.core.setAndAwait
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class NotificationRequestCacheRepository(
    private val reactiveRedisTemplate: ReactiveRedisTemplate<String, String>,
    @Value("\${notification-service.cache.ttl}") val ttl: Long
) {
    companion object val reactiveValueOps: ReactiveValueOperations<String, String> = reactiveRedisTemplate.opsForValue()

    suspend fun addNewNotificationRequest(notificationRequest: NotificationRequest) = coroutineScope {
        launch {
            val key = generateKey(notificationRequest)
            reactiveValueOps.setAndAwait(key, key, Duration.ofMinutes(ttl))
        }
    }

    suspend fun wasAlreadySent(notificationRequest: NotificationRequest): Deferred<Boolean> = coroutineScope {
        return@coroutineScope async {
            val result = reactiveValueOps.get(generateKey(notificationRequest)).awaitSingleOrNull()
            result != null
        }
    }

    private fun generateKey(notificationRequest: NotificationRequest) =
        notificationRequest.notificationMessage + notificationRequest.accountId
}
