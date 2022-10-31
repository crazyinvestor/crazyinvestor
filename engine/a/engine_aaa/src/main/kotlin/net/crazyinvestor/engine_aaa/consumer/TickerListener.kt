package net.crazyinvestor.engine_aaa.consumer

import net.crazyinvestor.engine_aaa.constants.NEW_TICKER_TOPIC
import net.crazyinvestor.engine_aaa.persistence.Ticker
import net.crazyinvestor.engine_aaa.service.TickerService
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.kafka.support.mapping.AbstractJavaTypeMapper
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
@KafkaListener(
    topics = [NEW_TICKER_TOPIC],
    groupId = "group-1",
    containerFactory = "tickerListenerFactory"
)
class TickerListener(val tickerService: TickerService){
    @KafkaHandler
    fun listen(
        @Payload ticker: Ticker,
        @Header(AbstractJavaTypeMapper.DEFAULT_CLASSID_FIELD_NAME) typeId: String?,
        @Header(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME) contentFieldName: Any?,
        @Header(KafkaHeaders.KEY) key: String?,
        @Header(KafkaHeaders.TIMESTAMP) timestamp: Any?,
        @Header(KafkaHeaders.RECEIVED_TOPIC) toppic: String,
        @Header(KafkaHeaders.OFFSET) offset: Long
    ): Unit {
        fun debug(): Unit {
            println()
            println()
            println()
            println((typeId to contentFieldName) to (key to timestamp))
            println(toppic to offset)
            println("receive ticker ${ticker}")
            println()
            println()
            println()
        }

        debug()
        tickerService
            .createTicker(ticker)
            .subscribe()
    }

    @KafkaHandler
    fun listen(
        s: String,
        @Header(AbstractJavaTypeMapper.DEFAULT_CLASSID_FIELD_NAME) typeId: String?,
        @Header(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME) contentFieldName: Any?,
        @Header(KafkaHeaders.KEY) key: String?,
        @Header(KafkaHeaders.TIMESTAMP) timestamp: Any?,
        @Header(KafkaHeaders.RECEIVED_TOPIC) toppic: String,
        @Header(KafkaHeaders.OFFSET) offset: Long
    ): Unit {
        println()
        println()
        println()
        println((typeId to contentFieldName) to (key to timestamp))
        println(toppic to offset)
        println("receive string ${s}")
        println()
        println()
        println()
    }
}