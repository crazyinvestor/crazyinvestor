package net.crazyinvestor.ticker_producer_engine.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient
import org.springframework.web.reactive.socket.client.WebSocketClient
import java.net.URI
import java.util.*

import net.crazyinvestor.ticker_producer_engine.enums.BithumbSymbol
import net.crazyinvestor.ticker_producer_engine.enums.BithumbTickTypes

@Configuration
class BithumbWSConnectionConfiguration(
    @Value("\${app.exchange.uri.bithumb}") val baseURL: String,
) {

    @Bean
    @Qualifier("bithumbClient")
    fun websocketClient(): WebSocketClient {
        return ReactorNettyWebSocketClient()
    }

    @Bean
    @Qualifier("bithumb")
    fun bithumbBaseURI(): URI {
     return URI.create(baseURL)
    }

    @Bean
    fun bithumbWebsocketSubscribeSymbolList(): List<BithumbSymbol> {
        val symbols: MutableList<BithumbSymbol> = LinkedList<BithumbSymbol>()
        symbols.add(BithumbSymbol.BTC_KRW)
        symbols.add(BithumbSymbol.ETH_KRW)
        return symbols.toList()
    }

    @Bean
    fun bithumbWebsocketSubscribeTickTypeList(): List<BithumbTickTypes> {
        val tickTypes: MutableList<BithumbTickTypes> = LinkedList<BithumbTickTypes>()
        tickTypes.add(BithumbTickTypes.HR_1)

        return tickTypes.toList()
    }
}