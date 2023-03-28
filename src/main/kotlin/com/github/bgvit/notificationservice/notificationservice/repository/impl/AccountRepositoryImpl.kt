package com.github.bgvit.notificationservice.notificationservice.repository.impl

import com.github.bgvit.notificationservice.notificationservice.model.Account
import com.github.bgvit.notificationservice.notificationservice.repository.AccountRepository
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class AccountRepositoryImpl(private val r2dbcEntityTemplate: R2dbcEntityTemplate) : AccountRepository {
    override suspend fun getById(id: String): Mono<Account> {
        return r2dbcEntityTemplate.selectOne(Query.query(Criteria.where("id").`is`(id)), Account::class.java)
    }

    override suspend fun save(account: Account): Mono<Void> {
        return r2dbcEntityTemplate.insert(account).then()
    }

    override suspend fun getAll(): Flux<Account> {
        return r2dbcEntityTemplate.select(Account::class.java).all()
    }
}
