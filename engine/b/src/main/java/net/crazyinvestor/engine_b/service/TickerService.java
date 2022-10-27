package net.crazyinvestor.engine_b.service;

import lombok.RequiredArgsConstructor;
import net.crazyinvestor.engine_b.dto.response.BithumbWSTickerResponse;
import net.crazyinvestor.engine_b.entity.*;
import net.crazyinvestor.engine_b.enums.ExchangeName;
import net.crazyinvestor.engine_b.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TickerService {
    private final ExchangeRepository exchangeRepo;
    private final SymbolRepository symbolRepo;
    private final TickerRepository tickerRepo;
    private final TickerHistoryRepository tickerHistoryRepo;
    private final QuoteRepository quoteRepo;

    @Transactional
    public void saveBithumbTicker(BithumbWSTickerResponse response) {
        String exchange = ExchangeName.BITHUMB.getExchangeName();
        String symbol = response.getSymbol();
        String tickType = response.getTickType();

        int year = Integer.parseInt(response.getDate().substring(0, 4));
        int month = Integer.parseInt(response.getDate().substring(4, 6));
        int day = Integer.parseInt(response.getDate().substring(6, 8));

        int hour = Integer.parseInt(response.getTime().substring(0, 2));
        int minute = Integer.parseInt(response.getTime().substring(2, 4));

        long timestamp = Timestamp.valueOf(LocalDateTime.of(year, month, day, hour, minute)).getTime();
        long now = Timestamp.valueOf(LocalDateTime.now()).getTime();

        double openPrice = Double.parseDouble(response.getOpenPrice());
        double highPrice = Double.parseDouble(response.getHighPrice());
        double lowPrice = Double.parseDouble(response.getLowPrice());
        double closePrice = Double.parseDouble(response.getClosePrice());
        double volume = Double.parseDouble(response.getVolume());

        Exchange exchangeObj = exchangeRepo.findByName(exchange).orElseGet(() -> {
            Exchange newItem = Exchange.builder()
                        .name(exchange)
                        .build();

            exchangeRepo.save(newItem);

            return newItem;
        });

        Symbol symbolObj = symbolRepo.findByExchangeAndName(exchangeObj, symbol).orElseGet(() -> {
            Symbol newItem = Symbol.builder()
                        .exchange(exchangeObj)
                        .name(symbol)
                        .build();

            symbolRepo.save(newItem);

            return newItem;
        });

        Ticker tickerObj = tickerRepo.findBySymbolAndTickType(symbolObj, tickType).orElseGet(() -> {
            Ticker newItem = Ticker.builder()
                    .symbol(symbolObj)
                    .tickType(tickType)
                    .build();

            tickerRepo.save(newItem);

            return newItem;
        });

        // TODO: IO 성능 최악으로 예상
        // 일정 부분 JPA 캐시에서 커버해 줄 지도? 실제 성능은 측정해봐야 할 듯
        // Issue #28
        Quote quoteObj = quoteRepo.findBySymbol(symbolObj).orElseGet(() -> {
            Quote newItem = Quote.builder()
                    .symbol(symbolObj)
                    .price(closePrice)
                    .createdAt(now)
                    .updatedAt(now)
                    .build();

            quoteRepo.save(newItem);

            return newItem;
        });

        quoteObj.setPrice(closePrice);
        quoteObj.setUpdatedAt(now);

        quoteRepo.save(quoteObj);

        TickerHistory tickerHistoryObj = TickerHistory.builder()
                .ticker(tickerObj)
                .timestamp(timestamp)
                .openPrice(openPrice)
                .highPrice(highPrice)
                .lowPrice(lowPrice)
                .closePrice(closePrice)
                .volume(volume)
                .createdAt(now)
                .updatedAt(now)
                .build();

        tickerHistoryRepo.save(tickerHistoryObj);
    }
}
