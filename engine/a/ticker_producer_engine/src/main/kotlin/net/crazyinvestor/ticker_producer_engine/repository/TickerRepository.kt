package net.crazyinvestor.ticker_producer_engine.repository

import net.crazyinvestor.ticker_producer_engine.entity.Symbol
import net.crazyinvestor.ticker_producer_engine.entity.Ticker

import org.springframework.stereotype.Repository
import java.util.*

@Repository
class TickerRepository {
    fun findBySymbolAndTickType(symbol: Symbol, tickType: String): Optional<Ticker> {
        println("MOCK REPOSITORY5 $symbol $tickType")

        return Optional.of(Ticker(null,symbol, tickType))
    }

    fun save(ticker: Ticker): Ticker {
        println("save() MOCK REPOSITORY5 $ticker")
        return ticker
    }
}