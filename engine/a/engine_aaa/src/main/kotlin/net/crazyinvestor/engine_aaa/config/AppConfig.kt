package net.crazyinvestor.engine_aaa.config

import net.crazyinvestor.engine_aaa.constants.NEW_TICKER_TOPIC
import org.apache.kafka.clients.admin.NewTopic

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

//@Configuration
//class  AppConfig(
//
//){
//    @Qualifier("bootstrapServers")
//    @Bean
//    fun bootstrapServers(): String {
//        return bootstrapServers
//    }
//
////    @Bean
////    fun consumerConfigs(): Map<String, Any> {
////        return mapOf(
////            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
////            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
////            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
////            ConsumerConfig.GROUP_ID_CONFIG to "group-1"
////        )
////    }
//}
//
