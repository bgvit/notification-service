package com.github.bgvit.notificationservice.notificationservice.config

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaAdminConfig(
    @Value(value = "\${spring.kafka.bootstrap-servers}")
    private val bootstrapAddress: String,

    @Value(value = "\${notification-service.kafka.partition-number}")
    private val partitionNumber: Int,

    @Value(value = "\${notification-service.kafka.replication-factor}")
    private val replicationFactor: Short,

    @Value(value = "\${notification-service.kafka.topic}")
    private val topic: String,

    @Value(value = "\${notification-service.kafka.group-id}")
    private val groupId: String

) {

    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        val configs: MutableMap<String, Any?> = HashMap()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        return KafkaAdmin(configs)
    }

    @Bean
    fun createNotificationConsumerTopic(): NewTopic {
        return NewTopic(topic, partitionNumber, replicationFactor)
    }
}
