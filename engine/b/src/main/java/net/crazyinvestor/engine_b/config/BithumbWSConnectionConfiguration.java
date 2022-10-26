package net.crazyinvestor.engine_b.config;

import net.crazyinvestor.engine_b.enums.BithumbSymbol;
import net.crazyinvestor.engine_b.enums.BithumbTickTypes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

@Configuration
public class BithumbWSConnectionConfiguration {
    private final String baseURL;

    public BithumbWSConnectionConfiguration(
            @Value("${app.exchange.uri.bithumb}") final String baseURL
    ) {
        this.baseURL = baseURL;
    }

    @Bean
    @Qualifier("bithumb")
    public WebSocketClient getWebsocketClient() {
        return new ReactorNettyWebSocketClient();
    }

    @Bean
    @Qualifier("bithumb")
    public URI getBithumbBaseURI() {
        return URI.create(baseURL);
    }

    @Bean
    public List<BithumbSymbol> getBithumbWebsocketSubscribeSymbolList() {
        List<BithumbSymbol> symbols = new LinkedList<>();
        symbols.add(BithumbSymbol.BTC_KRW);
        symbols.add(BithumbSymbol.ETH_KRW);

        return symbols;
    }

    @Bean
    public List<BithumbTickTypes> getBithumbWebsocketSubscribeTickTypeList() {
        List<BithumbTickTypes> tickTypes = new LinkedList<>();
        tickTypes.add(BithumbTickTypes.HR_1);

        return tickTypes;
    }

}
