package com.github.bgvit.notificationservice.notificationservice.controller

import com.github.bgvit.notificationservice.notificationservice.model.dto.NotificationRequest
import com.github.bgvit.notificationservice.notificationservice.service.NotificationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/notification")
class NotificationController(
    private val notificationService: NotificationService
) {

    @PostMapping
    suspend fun addNewNotification(@RequestBody notificationRequest: NotificationRequest): ResponseEntity<String> {
        return notificationService.process(notificationRequest)
    }
}
