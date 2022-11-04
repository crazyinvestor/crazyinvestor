package net.crazyinvestor.engine_aaa.consumer

import net.crazyinvestor.engine_aaa.constants.NEW_TICKER_TOPIC
import net.crazyinvestor.engine_aaa.constants.TICK_INFO_TOPIC
import net.crazyinvestor.engine_aaa.dto.RecentCurrencyInfoDto
import net.crazyinvestor.engine_aaa.persistence.Ticker
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import org.springframework.kafka.support.serializer.JsonDeserializer
import reactor.kafka.receiver.ReceiverOptions
import java.util.*

@Configuration
@EnableConfigurationProperties(KafkaProperties::class)
class KafkaReceiverConfiguration(
    val kafkaProperties: KafkaProperties
) {
//    @Bean
//    fun reactiveKafkaConsumerTemplateForTicker(): ReactiveKafkaConsumerTemplate<String, Ticker> {
//        val receiverOptions =
//            receiverOptions<String, Ticker>(groupId = "group-1", topicName = NEW_TICKER_TOPIC)
//        return ReactiveKafkaConsumerTemplate(receiverOptions)
//    }

    @Bean
    fun reactiveKafkaConsumerTemplateForRecentCurrenyInfoDto(): ReactiveKafkaConsumerTemplate<String, RecentCurrencyInfoDto> {
        val receiverOptions =
            receiverOptions<String, RecentCurrencyInfoDto>(groupId = "group-2", topicName = TICK_INFO_TOPIC)
        return ReactiveKafkaConsumerTemplate(receiverOptions)
    }

    @Bean
    fun stringDeserializer(): StringDeserializer {
        return StringDeserializer()
    }

    @Bean
    fun <T> jsonDeserializer(): JsonDeserializer<T> {
        return JsonDeserializer()
    }

    fun <K, V> receiverOptions(groupId: String, topicName: String): ReceiverOptions<K, V> {
        val props: MutableMap<String, Any> = kafkaProperties.buildConsumerProperties()
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        props[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        props[ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG] = true

        return ReceiverOptions.create<K, V>(props)
            .subscription(mutableListOf(topicName))
    }
}

//    @Bean
//    fun jsonDeserializer(): JsonDeserializer<RecentCurrencyInfoDto> {
//        return JsonDeserializer()
//    }
