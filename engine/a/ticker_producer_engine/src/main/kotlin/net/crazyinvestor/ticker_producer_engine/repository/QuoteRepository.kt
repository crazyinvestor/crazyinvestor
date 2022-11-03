package net.crazyinvestor.ticker_producer_engine.repository;

import net.crazyinvestor.ticker_producer_engine.entity.Quote
import net.crazyinvestor.ticker_producer_engine.entity.Symbol
import org.springframework.stereotype.Repository

import java.util.Optional;

@Repository
class QuoteRepository {
    fun findBySymbol(symbol: Symbol): Optional<Quote> {
        println("MOCK REPOSITORY2 $symbol")

        return Optional.of(Quote(1L, symbol, null, null, symbol.quote?.updatedAt))
    }

    fun save(quote: Quote): Quote {
        println("save MOCK REPOSITORY2 $quote")
        return quote
    }
}
