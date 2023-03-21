package com.github.bgvit.notificationservice.notificationservice.config

import com.github.bgvit.notificationservice.notificationservice.model.Notification
import jakarta.annotation.PreDestroy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig(private val factory: RedisConnectionFactory) {

    @Bean
    fun reactiveRedisTemplate(factory: ReactiveRedisConnectionFactory): ReactiveRedisTemplate<String, Notification> {
        return ReactiveRedisTemplate(
            factory,
            RedisSerializationContext
                .newSerializationContext<String, Notification>(StringRedisSerializer())
                .value(Jackson2JsonRedisSerializer(Notification::class.java))
                .build()
        )
    }

    @PreDestroy
    fun cleanRedis() {
        factory.connection.serverCommands().flushDb()
    }
}
