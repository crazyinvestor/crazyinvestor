package net.crazyinvestor.engine_aaa.service

import net.crazyinvestor.engine_aaa.persistence.TickerRepository
import net.crazyinvestor.engine_aaa.persistence.Ticker
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class TickerServiceImpl(
    private val tickerRepository: TickerRepository
): TickerService {
    override fun createTicker(ticker: Ticker): Mono<Ticker> {
        val saved: Mono<Ticker> = tickerRepository.save(ticker)
        return saved
    }
}