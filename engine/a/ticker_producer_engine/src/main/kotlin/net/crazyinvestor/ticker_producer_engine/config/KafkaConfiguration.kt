package net.crazyinvestor.ticker_producer_engine.config

import net.crazyinvestor.dto.TickTxDto
import net.crazyinvestor.engine_aaa.dto.TickInfoDto
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.kafka.support.serializer.JsonSerializer
import reactor.kafka.sender.SenderOptions

@Configuration
@EnableKafka
class KafkaConfiguration {
    @Bean
    fun reactiveKafkaProducerTemplateForTickerOp(): ReactiveKafkaProducerTemplate<String, TickInfoDto> {
        return ReactiveKafkaProducerTemplate<String, TickInfoDto>(senderOptions<String, TickInfoDto>())
    }

    @Bean
    fun reactiveKafkaProducerTemplateForTickerTxOp(): ReactiveKafkaProducerTemplate<String, TickTxDto> {
        return ReactiveKafkaProducerTemplate<String, TickTxDto>(senderOptions<String, TickTxDto>())
    }

    @Bean
    fun jsonSerializer(): JsonSerializer<TickInfoDto> {
        return JsonSerializer()
    }

    fun <K, V> senderOptions(): SenderOptions<K, V> {
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG
        val producerProps: MutableMap<String, Any> = mutableMapOf()
        producerProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:29092"
        producerProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        producerProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java

        return SenderOptions.create<K,V>(producerProps)
            .maxInFlight(1024)

    }
}