package net.crazyinvestor.ticker_producer_engine.deprecated.repository;

import net.crazyinvestor.ticker_producer_engine.entity.Exchange
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class ExchangeRepository {
    fun findByName(name: String): Optional<Exchange> {
        println("MOCK REPOSITORY1 $name")
        return Optional.of(Exchange(null, name))
    }
    fun save(exchange: Exchange): Exchange {
        println("save() MOCK REPOSITORY1 $exchange")
        return exchange
    }
}
