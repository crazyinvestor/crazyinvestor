package net.crazyinvestor.ticker_producer_engine.repository

import net.crazyinvestor.ticker_producer_engine.entity.Exchange
import net.crazyinvestor.ticker_producer_engine.entity.Symbol
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class SymbolRepository {
    fun findByExchangeAndName(exchange: Exchange, name: String): Optional<Symbol> {
        println("MOCK REPOSITORY3 $exchange $name")
        return Optional.of(Symbol(null, name, exchange, null))
    }

    fun save(symbol: Symbol): Symbol {
        println("save() MOCK REPOSITORY3 $symbol")
        return symbol
    }
}