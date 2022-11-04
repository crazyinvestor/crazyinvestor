package net.crazyinvestor.engine_aaa.config

import net.crazyinvestor.engine_aaa.constants.NEW_TICKER_TOPIC
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.common.serialization.StringDeserializer

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.kafka.config.TopicBuilder


@Profile("dev")
@Configuration
class KafkaTopicConfigDevOnly {
    @Bean
    fun newTickerTopic(): NewTopic {
        return TopicBuilder
            .name(NEW_TICKER_TOPIC)
            .build()
    }
}
