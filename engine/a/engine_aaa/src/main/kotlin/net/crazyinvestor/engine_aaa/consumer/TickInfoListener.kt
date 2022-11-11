package net.crazyinvestor.engine_aaa.consumer

import net.crazyinvestor.engine_aaa.config.TICK_INFO_TOPIC
import net.crazyinvestor.engine_aaa.dto.TickInfoDto
import net.crazyinvestor.engine_aaa.persistence.RecentCurrencyInfo
import org.springframework.data.cassandra.core.CassandraOperations
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import reactor.util.Loggers

const val TICK_INFO_LISTENER_CONTAINER_FACTORY_NAME = "tickInfoListenerContainerFactory"

@KafkaListener(
    topics = [TICK_INFO_TOPIC],
    concurrency = "3",
    containerFactory = TICK_INFO_LISTENER_CONTAINER_FACTORY_NAME
)
class TickInfoListener(
    private val cassandraTemplate: CassandraOperations
) {

    @KafkaHandler
    fun listen(
        @Payload dto: TickInfoDto,
        @Header(KafkaHeaders.OFFSET) offset: Long,
        acknowledgment: Acknowledgment
    ) {
        cassandraTemplate.insert(RecentCurrencyInfo.fromRecentCurrenyInfoDto(dto))
        acknowledgment.acknowledge()

        Loggers.getLogger(TickInfoListener::class.java)
            .debug("[SUCCESS] CASSANDRA, offset=$offset")
    }

    @KafkaHandler(isDefault = true)
    fun unknown(obj: Any) {
        TODO()
        // logger.debug("[${TICK_INFO_TOPIC}] ERROR. UNKNOWN TYPE RECEIVED.")
    }
}

