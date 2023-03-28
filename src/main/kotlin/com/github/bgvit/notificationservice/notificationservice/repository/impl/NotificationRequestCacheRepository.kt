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
            try {
                val key = generateKey(notificationRequest)
                reactiveValueOps.setAndAwait(key, key, Duration.ofMinutes(ttl))
            } catch (t: Throwable) {
                /*If the code have fallen here, probably some integration problem happened with redis.
                * The application will ignore in this case. I'll put logo here later. todo()*/
            }
        }
    }

    suspend fun wasAlreadySent(notificationRequest: NotificationRequest): Deferred<Boolean> = coroutineScope {
        return@coroutineScope async {
            try {
                val result = reactiveValueOps.get(generateKey(notificationRequest)).awaitSingleOrNull()
                result != null
            } catch (t: Throwable) {
                /* Some problem happened with integration on Redis, if the codes have felt here.
                 In this case, the application will ignore these validations.
                 I will put log here later. todo() */
                false
            }
        }
    }

    private fun generateKey(notificationRequest: NotificationRequest) =
        notificationRequest.text + notificationRequest.accountId
}
