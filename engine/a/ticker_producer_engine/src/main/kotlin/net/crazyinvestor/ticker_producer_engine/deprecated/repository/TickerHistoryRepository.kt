package net.crazyinvestor.ticker_producer_engine.deprecated.repository

import net.crazyinvestor.ticker_producer_engine.entity.Ticker
import net.crazyinvestor.ticker_producer_engine.entity.TickerHistory
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class TickerHistoryRepository {
    fun findByTicker(ticker: Ticker): Optional<TickerHistory> {
        println("MOCK REPOSITORY4 $ticker")
        return Optional.of(TickerHistory(null, ticker, null,
            null,null,null,
            null,null,null,null))
    }
    fun save(tickerHistory: TickerHistory): TickerHistory {
        println("save() MOCK REPOSITORY4 $tickerHistory")
        return tickerHistory
    }
}