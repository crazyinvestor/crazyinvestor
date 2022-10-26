package net.crazyinvestor.engine_b.service;

import lombok.RequiredArgsConstructor;
import net.crazyinvestor.engine_b.dto.response.BithumbWSTickerResponse;
import net.crazyinvestor.engine_b.entity.Ticker;
import net.crazyinvestor.engine_b.enums.Exchange;
import net.crazyinvestor.engine_b.repository.TickerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TickerService {
    private final TickerRepository repo;

    @Transactional
    public void saveBithumbTicker(BithumbWSTickerResponse response) {
        String exchange = Exchange.BITHUMB.getExchangeName();
        String symbol = response.getSymbol();
        String tickType = response.getTickType();

        int year = Integer.parseInt(response.getDate().substring(0, 4));
        int month = Integer.parseInt(response.getDate().substring(4, 6));
        int day = Integer.parseInt(response.getDate().substring(6, 8));

        int hour = Integer.parseInt(response.getTime().substring(0, 2));
        int minute = Integer.parseInt(response.getTime().substring(2, 4));

        long timestamp = Timestamp.valueOf(LocalDateTime.of(year, month, day, hour, minute)).getTime();

        Ticker ticker = repo.findByExchangeAndSymbolAndTickTypeAndTimestamp(
                exchange,
                symbol,
                tickType,
                timestamp
        ).orElse(Ticker.builder()
                .exchange(exchange)
                .symbol(symbol)
                .tickType(tickType)
                .timestamp(timestamp)
                .build()
        );

        ticker.setOpenPrice(Double.parseDouble(response.getOpenPrice()));
        ticker.setHighPrice(Double.parseDouble(response.getHighPrice()));
        ticker.setLowPrice(Double.parseDouble(response.getLowPrice()));
        ticker.setClosePrice(Double.parseDouble(response.getClosePrice()));
        ticker.setVolume(Double.parseDouble(response.getVolume()));

        repo.save(ticker);
    }
}
