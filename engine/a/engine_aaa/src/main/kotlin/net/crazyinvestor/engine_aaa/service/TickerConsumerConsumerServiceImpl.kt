package net.crazyinvestor.engine_aaa.service

import net.crazyinvestor.engine_aaa.persistence.TickerRepository
import net.crazyinvestor.engine_aaa.persistence.Ticker
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

// TODO deprecate 시키기
@Service
class TickerConsumerConsumerServiceImpl(
    private val tickerRepository: TickerRepository
): TickerConsumerService {
    override fun createTicker(ticker: Ticker): Mono<Ticker> {
        val saved: Mono<Ticker> = tickerRepository.save(ticker)
        return saved
    }
}