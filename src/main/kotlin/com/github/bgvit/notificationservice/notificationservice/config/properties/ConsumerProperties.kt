package com.github.bgvit.notificationservice.notificationservice.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "notification-service.kafka.consumer")
data class ConsumerProperties(
    val partitionNumber: Int,
    val replicationFactor: Short,
    val topic: String,
    val groupId: String
)
