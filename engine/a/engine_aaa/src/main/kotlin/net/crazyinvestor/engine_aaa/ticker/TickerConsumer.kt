package net.crazyinvestor.engine_aaa.ticker

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

@Component
class TickerConsumer(
    val tickerService: TickerServiceImpl
){

//    Consumer
//    @RetryableTopic(attempts = "5", backoff = @Backoff(delay = 2_000, maxDelay = 10_000, multiplier = 2))
    @KafkaListener(id = "group-1", topics = ["new-ticker"])
    fun listen(
        value: String?,
        @Header(KafkaHeaders.RECEIVED_TOPIC) toppic: String,
        @Header(KafkaHeaders.OFFSET) offset: Long
    ): Unit {
        if(value == null || value == "") return
        println(value)

        tickerService.createTicker(value)
            .subscribe()
    }
}