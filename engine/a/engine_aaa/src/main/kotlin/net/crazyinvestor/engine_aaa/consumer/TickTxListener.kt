package net.crazyinvestor.engine_aaa.consumer

import net.crazyinvestor.dto.TickTxDto
import net.crazyinvestor.engine_aaa.config.TICK_TX_TOPIC
import net.crazyinvestor.engine_aaa.persistence.TickerTransaction
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate
import org.springframework.kafka.annotation.DltHandler
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.kafka.support.Acknowledgment
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import reactor.core.publisher.Mono
import reactor.util.Loggers
import java.time.Duration

const val TICK_TX_LISTENER_CONTAINER_FACTORY_NAME = "tickTxListenerContainerFactory"

@KafkaListener(
    topics = [TICK_TX_TOPIC],
    concurrency = "3",
    containerFactory = TICK_TX_LISTENER_CONTAINER_FACTORY_NAME
)
class TickTxListener (
    private val reactiveCassandraTemplate: ReactiveCassandraTemplate
) {
    companion object {
        val logger = Loggers.getLogger(TickTxListener::class.java)
    }

//    @RetryableTopic(
//        attempts = "10",
//        backoff = Backoff(delay = 200L, multiplier = 3.0, maxDelay = 0),
//        numPartitions = "3",
//        dltStrategy = DltStrategy.ALWAYS_RETRY_ON_ERROR,
//        exclude = [
//            DeserializationException::class,
//            SerializationException::class,
//            MessageConversionException::class,
//            ConversionException::class,
//            MethodArgumentResolutionException::class,
//            NoSuchMethodError::class,
//            ClassCastException::class
//        ]
//    )
    // @RetryableTopic
    @KafkaHandler
    fun listen(
        @Payload dto: TickTxDto,
        @Header(KafkaHeaders.OFFSET) offset: Long,
        acknowledgment: Acknowledgment
    ) {
        logger.debug("CONSUME TX offset=${offset}, tickerId=${dto.tickerId}, tickerName=${dto.tickerName}, contPrice=${dto.contPrice}")

        val doAck = {
            logger.debug("SUCCESS doAck()")
            acknowledgment.acknowledge()
        }
        val doNack = { t: Throwable ->
            logger.debug("ERROR doNack()")
            acknowledgment.nack(Duration.ofMillis(1000L))
            Mono.empty<TickerTransaction>()
        }

        reactiveCassandraTemplate
            .insert(TickerTransaction.fromDto(dto))
            .doOnError {
                logger.debug("ERROR CASSANDRA, offset=${offset}")
            }
            .doOnSuccess {
                logger.debug("SUCCESS CASSANDRA, offset=${offset}")
            }
            .onErrorResume(doNack)
            .doAfterTerminate(doAck)
            .subscribe()
    }

    @KafkaHandler(isDefault = true)
    fun unknown(obj: Any) {
        logger.debug("[$TICK_TX_TOPIC] ERROR. UNKNOWN TYPE RECEIVED.")
    }

    //@DltHandler
   // fun handleDlt(tickTxDto: TickTxDto) {
    //    logger.debug("[DLT] tickTxDto=$tickTxDto")
   // }
}

// private val reactiveKafkaConsumerTemplate: ReactiveKafkaConsumerTemplate<String, TickerTransactionDto>,
//    fun run(args: ApplicationArguments?) {
//        reactiveKafkaConsumerTemplate
//            .receiveAutoAck()
//            .retry(5)
//            .flatMap(getConsumeFunction())
//            .subscribe()
//    }
//
//    private fun getConsumeFunction(): (t: ConsumerRecord<String, TickerTransactionDto>) -> Publisher<out TickerTransaction> = lambda@ {
//        logger.debug("CONSUME TX tickerId=${it.value().tickerId}, tickerName=${it.value().tickerName}, contPrice=${it.value().contPrice}")
//        val dto = it.value()
//
//        return@lambda reactiveCassandraTemplate
//            .insert(TickerTransaction.fromDto(dto))
//    }
