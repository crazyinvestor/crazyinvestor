package net.crazyinvestor.ticker_producer_engine.service

import net.crazyinvestor.engine_aaa.dto.TickInfoDto
import net.crazyinvestor.ticker_producer_engine.dto.bithumb.response.BithumbWSTickerResponse
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.stereotype.Service
import reactor.util.Loggers
import java.time.LocalDateTime
import java.util.*

@Service
class TickerOpService(
    val reactiveKafkaProducerTemplate: ReactiveKafkaProducerTemplate<String, TickInfoDto>
){
    companion object {
        val logger = Loggers.getLogger(TickerOpService::class.java)
    }

    fun saveBithumbTicker(response: BithumbWSTickerResponse) {
        val dto = with(response){
            val id = UUID.nameUUIDFromBytes(symbol!!.toByteArray())
            val year: Int = date!!.substring(0, 4).toInt()
            val month: Int = date.substring(4, 6).toInt()
            val day: Int = date.substring(6, 8).toInt()
            val hour: Int = time!!.substring(0, 2).toInt()
            val minute: Int = time.substring(2, 4).toInt()
            val timestamp = LocalDateTime.of(year, month, day, hour, minute)

            TickInfoDto(
                id = id,
                tickerName = symbol, // tickerName
                tickType = tickType!!,
                timestamp = timestamp,
                openPrice = openPrice!!.toDouble().toString(),
                highPrice = highPrice!!.toDouble().toString(),
                lowPrice = lowPrice!!.toDouble().toString(),
                closePrice = closePrice!!.toDouble().toString(),
                createdAt = LocalDateTime.now(),
                date = "${year}${month}${day}".toInt(),
                volume = volume!!.toDouble().toString(),
                hour = hour,
                value = value!!,
                sellVolume = sellVolume!!,
                buyVolume = buyVolume!!,
                prevClosePrice = prevClosePrice!!,
                chgRate = chgRate!!,
                chgAmt = chgAmt!!,
                volumePower = volumePower!!
            )
        }

        logger.debug("PRODUCE $dto")
        reactiveKafkaProducerTemplate
            .send(ProducerRecord("tick-info", dto))
            .subscribe()
    }
}

// val symbol: String = response.symbol!!
// val tickType: String = response.tickType!!
//        val (date, time) = response.date!! to response.time!!

//        val year: Int = date.substring(0, 4).toInt()
//        val month: Int = date.substring(4, 6).toInt()
//        val day: Int = date.substring(6, 8).toInt()
//        val hour: Int = time.substring(0, 2).toInt()
//        val minute: Int = time.substring(2, 4).toInt()

//        val timestamp2 = LocalDateTime.of(year, month, day, hour, minute)
//        val openPrice: Double = response.openPrice!!.toDouble()
//        val highPrice: Double = response.highPrice!!.toDouble()
//        val lowPrice: Double = response.lowPrice!!.toDouble()
//        val closePrice: Double = response.closePrice!!.toDouble()
//        val volume: Double = response.volume!!.toDouble()
//        val id = when(symbol){
//            "BTC_KRW" -> UUID.fromString("3889ed90-5bf2-11ed-ae98-2b786da56122")
//            "ETH_KRW" -> UUID.fromString("692323f0-5bf1-11ed-ae98-2b786da56122")
//            else -> TODO("ticker 새로 등록하는 경우 핸들링하기. 배치 작업?")
//        }!!
//
//        val openPrice2 = openPrice.toString()
//        val highPrice2 = highPrice.toString()
//        val closePrice2 = closePrice.toString()
//        val createdAt = LocalDateTime.now()
//        val date2: Int = "${year}${month}${day}".toInt()
//val tickerHistoryObj: TickerHistory = TickerHistory(
//    1L,
//    tickerObj,
//    timestamp,
//    openPrice,
//    highPrice,
//    lowPrice,
//    closePrice,
//    volume,
//    now,
//    now
//)
//        val exchangeObj: Exchange = exchangeRepo
//            .findByName(exchange)
//            .orElseGet {
//                val newItem: Exchange = Exchange(1L, exchange)
//                exchangeRepo.save(newItem)
//                newItem
//            }
//
//        val symbolObj: Symbol = symbolRepo
//            .findByExchangeAndName(exchangeObj, symbol)
//            .orElseGet {
//                val newItem: Symbol = Symbol(
//                    null,
//                    symbol,
//                    exchangeObj,
//                    null
//                    )
//                symbolRepo.save(newItem)
//                newItem
//            }
//
//        val tickerObj: Ticker = tickerRepo
//            .findBySymbolAndTickType(symbolObj, tickType)
//            .orElseGet {
//                val newItem = Ticker(null, symbolObj, tickType)
//                tickerRepo.save(newItem)
//                newItem
//            }
//
//        val quoteObj: Quote = quoteRepo
//            .findBySymbol(symbolObj)
//            .orElseGet {
//                val newItem: Quote = Quote(
//                    1L,
//                    symbolObj,
//                    closePrice,
//                    now,
//                    now
//                )
//                quoteRepo.save(newItem)
//                newItem
//            }
//
//        quoteObj.price = closePrice
//        quoteObj.updatedAt = now
//        quoteRepo.save(quoteObj)