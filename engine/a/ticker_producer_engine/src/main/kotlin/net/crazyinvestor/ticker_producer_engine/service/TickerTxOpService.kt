package net.crazyinvestor.ticker_producer_engine.service

import net.crazyinvestor.dto.TickTxDto
import net.crazyinvestor.ticker_producer_engine.dto.bithumb.response.BithumbWSTransactionResponseContent
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.stereotype.Service
import reactor.util.Loggers
import java.time.LocalDateTime
import java.util.*

@Service
class TickerTxOpService(
    val reactiveKafkaProducerTemplate: ReactiveKafkaProducerTemplate<String, TickTxDto>
){
    companion object {
        val logger = Loggers.getLogger(TickerTxOpService::class.java)
    }

    fun produce(response: BithumbWSTransactionResponseContent) {
        val dto = with(response){
            val id = UUID.nameUUIDFromBytes(symbol!!.toByteArray())

            val timestamp = contDtm!!.split("-|:|\\.|\\s".toRegex())
                .let {
                    val (year, month, day) = it.subList(0, 3).map(String::toInt)
                    val (hour, minute, second) = it.subList(3, 6).map(String::toInt)
                    val ms = it.last().toInt()

                    LocalDateTime.of(year, month, day, hour, minute, second, ms*1000)
                }

            TickTxDto(
                tickerId = id,
                tickerName = symbol,
                buySellGb = buySellGb!!,
                contPrice = contPrice!!,
                contQty = contQty!!,
                contAmt = contAmt!!,
                contDtm = timestamp,
                updn = updn!!,
                date = "${timestamp.getYear()}${String.format("%02d", timestamp.getMonthValue())}${String.format("%02d",timestamp.getDayOfMonth())}".toInt(),
                hour = timestamp.getHour()
            )
        }
        logger.debug("PRODUCE TX $dto")
        reactiveKafkaProducerTemplate
            .send(ProducerRecord("tick-transaction", dto))
            .subscribe()
    }
}