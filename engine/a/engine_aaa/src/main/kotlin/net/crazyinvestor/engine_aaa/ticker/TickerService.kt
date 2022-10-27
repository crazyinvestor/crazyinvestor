package net.crazyinvestor.engine_aaa.ticker

import reactor.core.publisher.Mono

interface TickerService {
    fun createTicker(value: String): Mono<Ticker>
}

