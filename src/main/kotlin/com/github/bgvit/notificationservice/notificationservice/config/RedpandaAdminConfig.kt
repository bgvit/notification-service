package com.github.bgvit.notificationservice.notificationservice.config

import com.github.bgvit.notificationservice.notificationservice.config.properties.ConsumerProperties
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class RedpandaAdminConfig(
    @Value(value = "\${spring.kafka.bootstrap-servers}")
    private val bootstrapAddress: String,
    val consumerProperties: ConsumerProperties
) {

    @Bean
    fun redpandaAdmin(): KafkaAdmin {
        val configs: MutableMap<String, Any?> = HashMap()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        return KafkaAdmin(configs)
    }

    @Bean
    fun createNotificationConsumerTopic(): NewTopic {
        return NewTopic(consumerProperties.topic, consumerProperties.partitionNumber, consumerProperties.replicationFactor)
    }
}
