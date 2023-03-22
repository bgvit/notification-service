package com.github.bgvit.notificationservice.notificationservice.repository.impl

import com.github.bgvit.notificationservice.notificationservice.model.Notification
import com.github.bgvit.notificationservice.notificationservice.repository.NotificationRepository
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class NotificationRepositoryImpl(private val r2dbcEntityTemplate: R2dbcEntityTemplate) : NotificationRepository {

    override suspend fun getAll(): Flux<Notification> {
        return r2dbcEntityTemplate.select(Notification::class.java).all()
    }

    override suspend fun save(notification: Notification): Mono<Void> {
        return r2dbcEntityTemplate.insert(notification).then()
    }

    override suspend fun getById(id: String): Mono<Notification> {
        return r2dbcEntityTemplate.selectOne(Query.query(Criteria.where("id").`is`(id)), Notification::class.java)
    }
}
