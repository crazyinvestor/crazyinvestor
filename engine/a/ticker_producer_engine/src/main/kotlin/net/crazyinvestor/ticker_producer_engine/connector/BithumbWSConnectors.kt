package net.crazyinvestor.ticker_producer_engine.connector

import net.crazyinvestor.ticker_producer_engine.enums.BithumbOpType
import net.crazyinvestor.ticker_producer_engine.hadler.BithumbWSResponseHandler
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient
import java.net.URI

@Configuration
class BithumbWSConnectors(
    @Value("\${app.exchange.uri.bithumb}") val baseURL: String,
    val bithumbWSResponseHandler: BithumbWSResponseHandler,
    val eventPublisher: ApplicationEventPublisher
) {
    @Bean
    @Qualifier("bithumb")
    fun bithumbBaseURI(): URI {
        return URI.create(baseURL)
    }

    @Bean
    fun tickerWSConnectorRunner(): ApplicationRunner{
       return BaseBithumbWSConnector(
           client = ReactorNettyWebSocketClient(),
           baseURI = bithumbBaseURI(),
           handler =  bithumbWSResponseHandler,
           eventPublisher = eventPublisher,
           opType = BithumbOpType.TICKER
       )
    }

    @Bean
    fun transactionWSConnectorRunner(): ApplicationRunner {
        return BaseBithumbWSConnector(
            client = ReactorNettyWebSocketClient(),
            baseURI = bithumbBaseURI(),
            handler =  bithumbWSResponseHandler,
            eventPublisher = eventPublisher,
            opType = BithumbOpType.TRANSACTION
        )
    }

    @Bean
    fun orderWSConnectorRunner(): ApplicationRunner {
        return BaseBithumbWSConnector(
            client = ReactorNettyWebSocketClient(),
            baseURI = bithumbBaseURI(),
            handler =  bithumbWSResponseHandler,
            eventPublisher = eventPublisher,
            opType = BithumbOpType.ORDERBOOK_DEPTH
        )
    }
}