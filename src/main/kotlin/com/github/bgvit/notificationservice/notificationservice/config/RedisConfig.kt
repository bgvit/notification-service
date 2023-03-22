package com.github.bgvit.notificationservice.notificationservice.config

import jakarta.annotation.PreDestroy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig(private val factory: RedisConnectionFactory) {

    @Bean
    fun reactiveRedisTemplate(
        factory: ReactiveRedisConnectionFactory?
    ): ReactiveRedisTemplate<String, String>? {
        val keySerializer = StringRedisSerializer()
        val valueSerializer = StringRedisSerializer()
        val builder: RedisSerializationContextBuilder<String, String> =
            RedisSerializationContext.newSerializationContext<String, String>(keySerializer)
        val context: RedisSerializationContext<String, String> = builder.value(valueSerializer).build()
        return ReactiveRedisTemplate(factory!!, context)
    }

    @PreDestroy
    fun cleanRedis() {
        factory.connection.serverCommands().flushDb()
    }
}
