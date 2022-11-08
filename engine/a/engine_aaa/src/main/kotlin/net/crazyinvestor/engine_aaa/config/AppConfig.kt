package net.crazyinvestor.engine_aaa.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.kafka.config.TopicBuilder

/** Names of Kafka Topics */

const val TICK_INFO_TOPIC: String = "tick-info"
const val TICK_TX_TOPIC: String = "tick-transaction"


/** Names of Kafka Consumer Groups */

const val CONSUMER_GROUP_ONE: String = "group-1"
const val CONSUMER_GROUP_TWO: String = "group-2"

/** Name of Cassandra Keyspace Name */
const val DEFAULT_KEYSPACE: String = "djdb"

@Profile("dev")
@Configuration
class KafkaTopicConfigDevOnly {

    @Bean
    fun tickTxTopic(): NewTopic {
        return TopicBuilder
            .name(TICK_TX_TOPIC)
            .partitions(3)
            .build()
    }

    @Bean
    fun tickInfoTopic(): NewTopic {
        return TopicBuilder
            .name(TICK_INFO_TOPIC)
            .partitions(3)
            .build()
    }
}

//////////////////////////////////////////
// TODO 새로운 이름의 암호화폐 생성 이벤트 (ex, LDJ_KRW 코인)
// const val NEW_TICKER_TOPIC: String = "new-ticker"
//    @Bean
//    fun newTickerTopic(): NewTopic {
//        return TopicBuilder
//            .name(NEW_TICKER_TOPIC)
//            .partitions(3)
//            .build()
//    }
