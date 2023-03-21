package com.github.bgvit.notificationservice.notificationservice.config

import com.github.bgvit.notificationservice.notificationservice.handler.NotificationHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RouterConfiguration {

    companion object val URL_PREFIX = "/v1/notification"

    @Bean
    fun NotificationRoutes(notificationHandler: NotificationHandler) = coRouter {
        POST(URL_PREFIX, notificationHandler::addNewNotification)
    }
}
