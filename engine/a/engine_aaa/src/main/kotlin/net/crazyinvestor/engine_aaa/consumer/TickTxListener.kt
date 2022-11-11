package net.crazyinvestor.engine_aaa.consumer

import net.crazyinvestor.dto.TickTxDto
import net.crazyinvestor.engine_aaa.config.TICK_TX_TOPIC
import net.crazyinvestor.engine_aaa.persistence.TickerTransaction
import org.springframework.data.cassandra.core.CassandraOperations
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import reactor.util.Loggers

const val TICK_TX_LISTENER_CONTAINER_FACTORY_NAME = "tickTxListenerContainerFactory"

@KafkaListener(
    topics = [TICK_TX_TOPIC],
    concurrency = "3",
    containerFactory = TICK_TX_LISTENER_CONTAINER_FACTORY_NAME
)
class TickTxListener (
    private val cassandraTemplate: CassandraOperations
) {
    @KafkaHandler
    fun listen(
        @Payload dto: TickTxDto,
        @Header(KafkaHeaders.OFFSET) offset: Long,
        acknowledgment: Acknowledgment
    ) {
        cassandraTemplate.insert(TickerTransaction.fromDto(dto))
        acknowledgment.acknowledge()

        Loggers.getLogger(TickTxListener::class.java)
            .debug("[SUCCESS TX] CASSANDRA, offset=$offset")
    }

    @KafkaHandler(isDefault = true)
    fun unknown(obj: Any) {
        TODO()
        // logger.debug("[$TICK_TX_TOPIC] ERROR. UNKNOWN TYPE RECEIVED.")
    }
}
