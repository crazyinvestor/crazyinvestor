package net.crazyinvestor.engine_aaa.service

import net.crazyinvestor.engine_aaa.persistence.Ticker
import reactor.core.publisher.Mono

interface TickerService {
    fun createTicker(ticker: Ticker): Mono<Ticker>
}
