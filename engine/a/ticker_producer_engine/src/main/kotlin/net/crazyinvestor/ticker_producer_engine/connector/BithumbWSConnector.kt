package net.crazyinvestor.ticker_producer_engine.connector

import net.crazyinvestor.ticker_producer_engine.dto.request.BithumbWSSubscribeRequest
import net.crazyinvestor.ticker_producer_engine.enums.BithumbOpType
import net.crazyinvestor.ticker_producer_engine.enums.BithumbSymbol
import net.crazyinvestor.ticker_producer_engine.enums.BithumbTickTypes
import net.crazyinvestor.ticker_producer_engine.enums.ExchangeName
import net.crazyinvestor.ticker_producer_engine.event.DisconnectEvent
import net.crazyinvestor.ticker_producer_engine.hadler.BithumbWSResponseHandler
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import org.springframework.web.reactive.socket.client.WebSocketClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.Logger
import reactor.util.Loggers
import java.net.URI

@Component
class BithumbWSConnector(
    @Qualifier("bithumbClient") val client: WebSocketClient,
    @Qualifier("bithumb") val baseURI: URI,
    symbols: List<BithumbSymbol>,
    tickTypes: List<BithumbTickTypes>,
    val handler: BithumbWSResponseHandler,
    val eventPublisher: ApplicationEventPublisher,
) : ApplicationRunner {

    companion object {
        private val logger: Logger = Loggers.getLogger(BithumbWSConnector::class.java)
    }

    val tickerRequestMessage: String = BithumbWSSubscribeRequest
        .fromOpType(BithumbOpType.TICKER, symbols, tickTypes)
        .toString()

    val transactionRequestMessage: String = BithumbWSSubscribeRequest
        .fromOpType(BithumbOpType.TRANSACTION, symbols, tickTypes)
        .toString()

    val orderbookRequestMessage: String = BithumbWSSubscribeRequest
        .fromOpType(BithumbOpType.ORDERBOOK_DEPTH, symbols, tickTypes)
        .toString()

    fun connect(): Mono<Void> {
        logger.debug("connect()")

        return client.execute(baseURI) { session: WebSocketSession ->
            logger.debug("execute(), session = $session")

            val tickerSubscribeMessage =
                session.textMessage(tickerRequestMessage)
            val transactionSubscribeMessage =
                session.textMessage(transactionRequestMessage)
            val orderbookSubscribeMessage =
                session.textMessage(orderbookRequestMessage)
            val subscribeRequest =
                Flux.just(
                    tickerSubscribeMessage,
                    transactionSubscribeMessage,
                    orderbookSubscribeMessage
                )

            val receiveCallback = session.receive()
                .map { obj: WebSocketMessage ->
                    obj.payloadAsText
                }
                .doOnNext {
                    logger.debug("doOnNext{...}, data = $it")
                    handler.handle(it)
                }
                .doOnTerminate {
                    logger.debug("doOnTerminate{...}, data = $this")
                    eventPublisher.publishEvent(DisconnectEvent(ExchangeName.BITHUMB))
                }
            logger.debug("created receiveCallback variable.")

            session
                .send(subscribeRequest)
                .and(receiveCallback)
        }
    }

    override fun run(args: ApplicationArguments) {
        logger.debug("run()")

        connect().subscribe()
    }

    @EventListener(DisconnectEvent::class)
    fun onDisconnectEvent(event: DisconnectEvent) {
        logger.debug("onDisconnectEvent(), event = $event")
        if (event.source != ExchangeName.BITHUMB) return
        connect().subscribe()
    }
}