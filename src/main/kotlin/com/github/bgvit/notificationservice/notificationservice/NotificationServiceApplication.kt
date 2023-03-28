package com.github.bgvit.notificationservice.notificationservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
@ConfigurationPropertiesScan("com.github.bgvit.notificationservice.notificationservice.config.properties")
@EnableR2dbcRepositories("com.github.bgvit.notificationservice.notificationservice.repository")
class NotificationServiceApplication

fun main(args: Array<String>) {
    runApplication<NotificationServiceApplication>(*args)
}
