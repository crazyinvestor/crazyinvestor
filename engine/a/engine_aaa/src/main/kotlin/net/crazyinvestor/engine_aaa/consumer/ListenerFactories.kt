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

@EnableKafka
@EnableConfigurationProperties(KafkaProperties::class)
@Configuration
class ListenerFactories(
    val kafkaProperties: KafkaProperties
): KafkaAutoConfiguration(kafkaProperties) {

    @Bean
    fun tickerListenerFactory(): ConcurrentKafkaListenerContainerFactory<String, Ticker> {
        val consumerProperties = this.kafkaProperties.buildConsumerProperties()
        println("========================================")
        println(consumerProperties)
        println()
        println()
        println("========================================")

        return ConcurrentKafkaListenerContainerFactory<String, Ticker>().apply {
            setConcurrency(3)
            consumerFactory = DefaultKafkaConsumerFactory(
                consumerProperties,
                StringDeserializer(),
                JsonDeserializer()
            )
        }
    }
}

//    @Bean
//    @Qualifier("consumerConfigs")
//    fun consumerConfigs(): Map<String, Any> {
//        return mapOf(
//            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
//            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
//            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java
//        )
//    }
