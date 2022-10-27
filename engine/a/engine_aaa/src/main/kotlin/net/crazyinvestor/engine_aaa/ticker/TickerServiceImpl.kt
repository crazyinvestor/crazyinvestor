package net.crazyinvestor.engine_aaa.ticker

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class TickerServiceImpl(
    private val tickerRepository: TickerRepository
): TickerService {

    override fun createTicker(value: String): Mono<Ticker> {
        val saved: Mono<Ticker> = tickerRepository.save(Ticker(id=value, name=value, quantity = 11))
        return saved
    }
}