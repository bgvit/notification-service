package com.github.bgvit.notificationservice.notificationservice.repository

import com.github.bgvit.notificationservice.notificationservice.model.Account
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface AccountRepository {
    suspend fun getById(id: String): Mono<Account>
    suspend fun save(account: Account): Mono<Void>
    suspend fun getAll(): Flux<Account>
}
