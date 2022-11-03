package net.crazyinvestor.ticker_producer_engine.service

import net.crazyinvestor.ticker_producer_engine.dto.response.BithumbWSTickerResponse
import net.crazyinvestor.ticker_producer_engine.entity.*
import net.crazyinvestor.ticker_producer_engine.enums.ExchangeName
import net.crazyinvestor.ticker_producer_engine.repository.*
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDateTime

@Service
class TickerService(
    val exchangeRepo: ExchangeRepository,
    val symbolRepo: SymbolRepository,
    val tickerRepo: TickerRepository,
    val tickerHistoryRepo: TickerHistoryRepository,
    val quoteRepo: QuoteRepository
){
    // @Transactional
    fun saveBithumbTicker(response: BithumbWSTickerResponse) {
        val exchange: String = ExchangeName.BITHUMB.exchangeName

        val symbol: String = response.symbol!!
        val tickType: String = response.tickType!!
        val (date, time) = response.date!! to response.time!!

        val year: Int = date.substring(0, 4).toInt()
        val month: Int = date.substring(4, 6).toInt()
        val day: Int = date.substring(6, 8).toInt()
        val hour: Int = time.substring(0, 2).toInt()
        val minute: Int = time.substring(2, 4).toInt()
        val timestamp = Timestamp.valueOf(LocalDateTime.of(year, month, day, hour, minute)).time
        val now = Timestamp.valueOf(LocalDateTime.now()).time

        val openPrice: Double = response.openPrice!!.toDouble()
        val highPrice: Double = response.highPrice!!.toDouble()
        val lowPrice: Double = response.lowPrice!!.toDouble()
        val closePrice: Double = response.closePrice!!.toDouble()
        val volume: Double = response.volume!!.toDouble()

        val exchangeObj: Exchange = exchangeRepo
            .findByName(exchange)
            .orElseGet {
                val newItem: Exchange = Exchange(1L, exchange)
                exchangeRepo.save(newItem)
                newItem
            }

        val symbolObj: Symbol = symbolRepo
            .findByExchangeAndName(exchangeObj, symbol)
            .orElseGet {
                val newItem: Symbol = Symbol(
                    null,
                    symbol,
                    exchangeObj,
                    null
                    )
                symbolRepo.save(newItem)
                newItem
            }

        val tickerObj: Ticker = tickerRepo
            .findBySymbolAndTickType(symbolObj, tickType)
            .orElseGet {
                val newItem = Ticker(null, symbolObj, tickType)
                tickerRepo.save(newItem)
                newItem
            }

        val quoteObj: Quote = quoteRepo
            .findBySymbol(symbolObj)
            .orElseGet {
                val newItem: Quote = Quote(
                    1L,
                    symbolObj,
                    closePrice,
                    now,
                    now
                )
                quoteRepo.save(newItem)
                newItem
            }

        quoteObj.price = closePrice
        quoteObj.updatedAt = now
        quoteRepo.save(quoteObj)

        val tickerHistoryObj: TickerHistory = TickerHistory(
            1L,
            tickerObj,
            timestamp,
            openPrice,
            highPrice,
            lowPrice,
            closePrice,
            volume,
            now,
            now
        )

        tickerHistoryRepo.save(tickerHistoryObj)
    }
}
