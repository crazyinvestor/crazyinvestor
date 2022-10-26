package net.crazyinvestor.engine_b.connector;

import net.crazyinvestor.engine_b.dto.*;
import net.crazyinvestor.engine_b.handler.BithumbWSResponseHandler;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Component
public class BithumbWSConnector implements ApplicationRunner {
    private static final Logger LOG = Logger.getLogger(BithumbWSConnector.class);

    private final WebSocketClient client;
    private final URI baseURI;
    private final List<BithumbSymbol> symbols;
    private final List<BithumbTickTypes> tickTypes;
    private final BithumbWSResponseHandler handler;

    public BithumbWSConnector(
            @Qualifier("bithumb") final WebSocketClient client,
            @Qualifier("bithumb") final URI baseURI,
            final List<BithumbSymbol> symbols,
            final List<BithumbTickTypes> tickTypes,
            final BithumbWSResponseHandler handler
    ) {
        this.client = client;
        this.baseURI = baseURI;
        this.symbols = symbols;
        this.tickTypes = tickTypes;
        this.handler = handler;
    }

    private BithumbWSSubscribeRequest generateTickerRequest(
            final List<BithumbSymbol> symbols,
            final List<BithumbTickTypes> tickTypes
    ) {
        BithumbOpType opType = BithumbOpType.TICKER;
        return new BithumbWSSubscribeRequest(opType, symbols, tickTypes);
    }

    private BithumbWSSubscribeRequest generateTransactionRequest(
            final List<BithumbSymbol> symbols
    ) {
        BithumbOpType opType = BithumbOpType.TRANSACTION;
        return new BithumbWSSubscribeRequest(opType, symbols, tickTypes);
    }

    private BithumbWSSubscribeRequest generateOrderbookDepthRequest(
            final List<BithumbSymbol> symbols
    ) {
        BithumbOpType opType = BithumbOpType.ORDERBOOK_DEPTH;
        return new BithumbWSSubscribeRequest(opType, symbols, tickTypes);
    }

    public Mono<Void> connect() {
        BithumbWSSubscribeRequest tickerRequest = generateTickerRequest(symbols, tickTypes);
        BithumbWSSubscribeRequest transactionRequest = generateTransactionRequest(symbols);
        BithumbWSSubscribeRequest orderbookRequest = generateOrderbookDepthRequest(symbols);

        return client.execute(baseURI, session -> {
            WebSocketMessage tickerSubscribeMessage = session.textMessage(tickerRequest.toString());
            WebSocketMessage transactionSubscribeMessage = session.textMessage(transactionRequest.toString());
            WebSocketMessage orderbookSubscribeMessage = session.textMessage(orderbookRequest.toString());

            Flux<WebSocketMessage> subscribeRequest = Flux.just(
                    tickerSubscribeMessage,
                    transactionSubscribeMessage,
                    orderbookSubscribeMessage
            );
            
            Flux<String> receiveCallback = session.receive()
                    .map(WebSocketMessage::getPayloadAsText)
                    .doOnNext(handler::handle);

            return session
                    .send(subscribeRequest)
                    .and(receiveCallback);
        });
    }

    @Override
    public void run(ApplicationArguments args) {
        this.connect().subscribe();
    }
}
