package net.crazyinvestor.ticker_producer_engine.config

import net.crazyinvestor.engine_aaa.dto.RecentCurrencyInfoDto
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
    fun reactiveKafkaProducerTemplate(): ReactiveKafkaProducerTemplate<String, RecentCurrencyInfoDto> {
        return ReactiveKafkaProducerTemplate<String, RecentCurrencyInfoDto>(senderOptions<String, RecentCurrencyInfoDto>())
    }

    @Bean
    fun jsonSerializer(): JsonSerializer<RecentCurrencyInfoDto> {
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