package net.crazyinvestor.ticker_producer_engine.connector

import net.crazyinvestor.ticker_producer_engine.dto.bithumb.request.BithumbWSSubscribeRequest
import net.crazyinvestor.ticker_producer_engine.enums.*
import net.crazyinvestor.ticker_producer_engine.event.DisconnectEvent
import net.crazyinvestor.ticker_producer_engine.hadler.BithumbWSResponseHandler
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import org.springframework.web.reactive.socket.client.WebSocketClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import reactor.util.Logger
import reactor.util.Loggers
import java.net.URI

class BaseBithumbWSConnector(
    val client: WebSocketClient,
    val baseURI: URI,
    val handler: BithumbWSResponseHandler,
    val eventPublisher: ApplicationEventPublisher,
    val opType: BithumbOpType
) : ApplicationRunner {
    companion object {
        private val logger: Logger = Loggers.getLogger(BaseBithumbWSConnector::class.java)
    }

    val opRequestMessage: String = BithumbWSSubscribeRequest
        .fromOpType(opType, HARD_CODED_SYMBOLS, TICK_TYPES)
        .toString()

    fun connect(): Mono<Void> {
        logger.debug("connect()")
        return client.execute(baseURI) { session: WebSocketSession ->
            logger.debug("execute(), session = $session")

            val subscribeRequest: Flux<WebSocketMessage> =
                Flux.just(session.textMessage(opRequestMessage))

            val receiveCallback = session
                .receive()
                .flatMap { obj: WebSocketMessage ->
                    logger.debug("flatMap {...}, obj = $obj")

                    obj.payloadAsText.toMono()
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