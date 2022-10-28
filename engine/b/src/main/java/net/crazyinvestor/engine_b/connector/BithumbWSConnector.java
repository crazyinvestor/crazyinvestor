package net.crazyinvestor.engine_b.connector;

import net.crazyinvestor.engine_b.dto.request.BithumbWSSubscribeRequest;
import net.crazyinvestor.engine_b.enums.BithumbOpType;
import net.crazyinvestor.engine_b.enums.BithumbSymbol;
import net.crazyinvestor.engine_b.enums.BithumbTickTypes;
import net.crazyinvestor.engine_b.enums.ExchangeName;
import net.crazyinvestor.engine_b.event.DisconnectEvent;
import net.crazyinvestor.engine_b.handler.BithumbWSResponseHandler;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Component
public class BithumbWSConnector implements ApplicationRunner {
    private static final Logger LOG = Logger.getLogger(BithumbWSConnector.class);

    private final WebSocketClient client;
    private final URI baseURI;
    private final List<BithumbSymbol> symbols;
    private final List<BithumbTickTypes> tickTypes;
    private final BithumbWSResponseHandler handler;
    private final ApplicationEventPublisher eventPublisher;

    private final String tickerRequestMessage;
    private final String transactionRequestMessage;
    private final String orderbookRequestMessage;

    public BithumbWSConnector(
            @Qualifier("bithumb") final WebSocketClient client,
            @Qualifier("bithumb") final URI baseURI,
            final List<BithumbSymbol> symbols,
            final List<BithumbTickTypes> tickTypes,
            final BithumbWSResponseHandler handler,
            final ApplicationEventPublisher eventPublisher
    ) {
        this.client = client;
        this.baseURI = baseURI;
        this.symbols = symbols;
        this.tickTypes = tickTypes;
        this.handler = handler;
        this.eventPublisher = eventPublisher;

        this.tickerRequestMessage = generateRequest(BithumbOpType.TICKER).toString();
        this.transactionRequestMessage = generateRequest(BithumbOpType.TRANSACTION).toString();
        this.orderbookRequestMessage = generateRequest(BithumbOpType.ORDERBOOK_DEPTH).toString();
    }

    private BithumbWSSubscribeRequest generateRequest(final BithumbOpType opType) {
        return BithumbWSSubscribeRequest.builder()
                .opType(opType)
                .symbols(symbols)
                .tickTypes(tickTypes)
                .build();
    }

    public Mono<Void> connect() {
        return client.execute(baseURI, session -> {
            WebSocketMessage tickerSubscribeMessage = session.textMessage(this.tickerRequestMessage);
            WebSocketMessage transactionSubscribeMessage = session.textMessage(this.transactionRequestMessage);
            WebSocketMessage orderbookSubscribeMessage = session.textMessage(this.orderbookRequestMessage);

            Flux<WebSocketMessage> subscribeRequest = Flux.just(
                    tickerSubscribeMessage,
                    transactionSubscribeMessage,
                    orderbookSubscribeMessage
            );
            
            Flux<String> receiveCallback = session.receive()
                    .map(WebSocketMessage::getPayloadAsText)
                    .doOnNext(handler::handle)
                    .doOnTerminate(() -> eventPublisher.publishEvent(new DisconnectEvent(ExchangeName.BITHUMB)));

            return session
                    .send(subscribeRequest)
                    .and(receiveCallback);
        });
    }

    @Override
    public void run(ApplicationArguments args) {
        this.connect().subscribe();
    }

    @EventListener(DisconnectEvent.class)
    public void onDisconnectEvent(DisconnectEvent event) {
        if (!Objects.equals(event.getSource(), ExchangeName.BITHUMB)) return;
        this.connect().subscribe();
    }
}
