package com.github.bgvit.notificationservice.notificationservice.model

import com.github.ksuid.KsuidGenerator
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.ZonedDateTime

@Table
data class Account(
    @Id
    val id: String = KsuidGenerator.generate().toString(),
    val accountName: String,
    val optIn: Boolean = false,
    val createdOn: ZonedDateTime = ZonedDateTime.now(),
    val updateOn: ZonedDateTime? = null
)
