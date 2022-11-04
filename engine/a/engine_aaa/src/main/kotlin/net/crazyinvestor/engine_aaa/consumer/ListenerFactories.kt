package net.crazyinvestor.engine_aaa.consumer

import net.crazyinvestor.engine_aaa.persistence.Ticker
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer


// TODO deprecate 시키기
@EnableKafka
@EnableConfigurationProperties(KafkaProperties::class)
@Configuration
class ListenerFactories(
    val kafkaProperties: KafkaProperties
): KafkaAutoConfiguration(kafkaProperties) {
    @Bean
    fun tickerListenerFactory(): ConcurrentKafkaListenerContainerFactory<String, Ticker> {
        val consumerProperties = this.kafkaProperties.buildConsumerProperties()

        return ConcurrentKafkaListenerContainerFactory<String, Ticker>().apply {
            setConcurrency(1)
            consumerFactory = DefaultKafkaConsumerFactory(
                consumerProperties,
                StringDeserializer(),
                JsonDeserializer()
            )
        }
    }
}
