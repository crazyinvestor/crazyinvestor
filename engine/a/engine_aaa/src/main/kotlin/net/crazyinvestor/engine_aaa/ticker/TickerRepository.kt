package net.crazyinvestor.engine_aaa.ticker

import org.springframework.data.cassandra.repository.AllowFiltering
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface TickerRepository : ReactiveCassandraRepository<Ticker, String> {
    @AllowFiltering
    fun findByNameContains(name: String): Mono<Ticker>
}