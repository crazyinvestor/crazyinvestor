//package net.crazyinvestor.ticker_producer_engine.connector
//
//import org.springframework.beans.factory.annotation.Qualifier
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient
//import org.springframework.web.reactive.socket.client.WebSocketClient
//import java.net.URI
//import java.util.*
//
//import net.crazyinvestor.ticker_producer_engine.enums.BithumbSymbol
//import net.crazyinvestor.ticker_producer_engine.enums.BithumbTickTypes
//import org.springframework.web.reactive.socket.adapter.ReactorNettyWebSocketSession
//
//@Configuration
//class BithumbWSConnectionConfiguration(
//    @Value("\${app.exchange.uri.bithumb}") val baseURL: String,
//) {
//
//    @Bean
//    @Qualifier("bithumbClientForTicker")
//    fun websocketClientForTicker(): WebSocketClient {
//        return ReactorNettyWebSocketClient()
//    }
//
//    @Bean
//    @Qualifier("bithumbClientForTransaction")
//    fun websocketClientForTransaction(): WebSocketClient {
//        return ReactorNettyWebSocketClient()
//    }
//
//    @Bean
//    @Qualifier("bithumbClientForOrder")
//    fun websocketClientForOrder(): WebSocketClient {
//        return ReactorNettyWebSocketClient()
//    }
//
//    @Bean
//    @Qualifier("bithumb")
//    fun bithumbBaseURI(): URI {
//     return URI.create(baseURL)
//    }
//}