package net.crazyinvestor.engine_aaa.consumer

import net.crazyinvestor.engine_aaa.config.TICK_INFO_TOPIC
import net.crazyinvestor.engine_aaa.dto.TickInfoDto
import net.crazyinvestor.engine_aaa.persistence.RecentCurrencyInfo
import org.apache.kafka.clients.consumer.ConsumerRecord
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

const val TICK_INFO_LISTENER_CONTAINER_FACTORY_NAME = "tickInfoListenerContainerFactory"

@KafkaListener(
    topics = [TICK_INFO_TOPIC],
    concurrency = "3",
    containerFactory = TICK_INFO_LISTENER_CONTAINER_FACTORY_NAME
)
class TickInfoListener(
    private val reactiveCassandraTemplate: ReactiveCassandraTemplate
) {
    companion object {
        val logger = Loggers.getLogger(TickInfoListener::class.java)
    }


    @KafkaHandler
    fun listen(
        @Payload dto: TickInfoDto,
        @Header(KafkaHeaders.OFFSET) offset: Long,
        acknowledgment: Acknowledgment
    ) {
        logger.debug("CONSUME offset=${offset}, id=${dto.id}, tickerName=${dto.tickerName}, closePrice=${dto.closePrice}")

        val doAck = {
            logger.debug("SUCCESS doAck()")
            acknowledgment.acknowledge()
        }
        val doNack = { t: Throwable ->
            logger.debug("ERROR doNack()")
            acknowledgment.nack(Duration.ofMillis(1000L))
            Mono.empty<RecentCurrencyInfo>()
        }

        reactiveCassandraTemplate
            .insert(RecentCurrencyInfo.fromRecentCurrenyInfoDto(dto))
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
        logger.debug("[${TICK_INFO_TOPIC}] ERROR. UNKNOWN TYPE RECEIVED.")
    }
}

/////////////////////
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
//        reactiveKafkaConsumerTemplate
//            .receiveAutoAck()
//            .retry(5)
//            .flatMap(getConsumeFunction())
//            .doOnError { it.printStackTrace(); println("EEERRRROR ERROR"); }
//    private fun getConsumeFunction(): (t: ConsumerRecord<String, RecentCurrencyInfoDto>) -> Publisher<out RecentCurrencyInfo> = lambda@ {
//        logger.debug("CONSUME id=${it.value().id}, tickerName=${it.value().tickerName}, closePrice=${it.value().closePrice}")
//        val tickerDto = it.value()
//
//        if(tickerDto.id != null){
//            return@lambda reactiveCassandraTemplate
//                .insert(RecentCurrencyInfo.fromRecentCurrenyInfoDto(tickerDto))
//        }
//
//        // CASE 클라이언트가 ticker 의 이름만 알고 ID 를 모르는 경우
//        val query: Query = Query.query(Criteria.where("ticker_name").`is`(tickerDto.tickerName))
//        reactiveCassandraTemplate
//            .selectOne(query, RecentCurrencyInfo::class.java)
//            .map { it.tickerId }
//            .flatMap { id ->
//                reactiveCassandraTemplate
//                    .insert(RecentCurrencyInfo.fromRecentCurrenyInfoDto(tickerDto.copy(id = id)))
//            }
//    }