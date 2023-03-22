package com.github.bgvit.notificationservice.notificationservice.service.factory

import com.github.bgvit.notificationservice.notificationservice.model.NotificationType
import com.github.bgvit.notificationservice.notificationservice.service.interfaces.NotificationSender
import org.springframework.stereotype.Component

@Component
class NotificationSenderFactory(strategySet: Set<NotificationSender>) {
    private val strategies: MutableMap<NotificationType, NotificationSender> = hashMapOf<NotificationType, NotificationSender>()

    init {
        createStrategy(strategySet)
    }

    private fun createStrategy(strategySet: Set<NotificationSender>) {
        strategySet.forEach { strategy ->
            strategies[strategy.getStrategyName()] = strategy
        }
    }

    fun findStrategy(notificationType: NotificationType): NotificationSender? {
        return strategies[notificationType]
    }
}
