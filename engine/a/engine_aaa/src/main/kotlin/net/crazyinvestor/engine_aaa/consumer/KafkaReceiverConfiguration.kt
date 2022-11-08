package net.crazyinvestor.engine_aaa.consumer

import net.crazyinvestor.dto.TickTxDto
import net.crazyinvestor.engine_aaa.config.CONSUMER_GROUP_ONE
import net.crazyinvestor.engine_aaa.config.TICK_INFO_TOPIC
import net.crazyinvestor.engine_aaa.config.TICK_TX_TOPIC
import net.crazyinvestor.engine_aaa.dto.TickInfoDto
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.CassandraConnectionFailureException
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.EnableKafkaRetryTopic
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.retrytopic.DltStrategy
import org.springframework.kafka.retrytopic.RetryTopicConfiguration
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder
import org.springframework.kafka.support.EndpointHandlerMethod
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer
import java.util.*

@EnableKafka
@EnableConfigurationProperties(KafkaProperties::class)
@Configuration
class KafkaReceiverConfiguration(
    private final val kafkaProperties: KafkaProperties,
    val reactiveCassandraTemplate: ReactiveCassandraTemplate
) {

    /** Listeners */

    @Bean
    fun tickInfoListener() = TickInfoListener(reactiveCassandraTemplate)

    @Bean
    fun tickTxListener() = TickTxListener(reactiveCassandraTemplate)

    /** Listener Container Factories */

    @Bean(TICK_TX_LISTENER_CONTAINER_FACTORY_NAME)
    fun tickTxListenerContainerFactory(
        template: KafkaTemplate<String, TickTxDto>,
        consumerFactory: ConsumerFactory<String, TickTxDto>
    ) = listenerContainerFactory<TickTxDto>(template, consumerFactory)

    @Bean(TICK_INFO_LISTENER_CONTAINER_FACTORY_NAME)
    fun tickInfoListenerContainerFactory(
        template: KafkaTemplate<String, TickInfoDto>,
        consumerFactory: ConsumerFactory<String, TickInfoDto>
    ) = listenerContainerFactory<TickInfoDto>(template, consumerFactory)

    private fun <V> listenerContainerFactory(
        template: KafkaTemplate<String, V>,
        consumerFactory: ConsumerFactory<String, V>,
        setConfigs: ConcurrentKafkaListenerContainerFactory<String, V>.(ConsumerFactory<String, V>) -> Unit = { /** DO NOTHING */ }
    ): ConcurrentKafkaListenerContainerFactory<String, V> {
        return ConcurrentKafkaListenerContainerFactory<String, V>().also {
            it.setDefaultConfigs(consumerFactory, template)
            it.setConfigs(consumerFactory)
        }
    }

    private fun <V> ConcurrentKafkaListenerContainerFactory<String, V>.setDefaultConfigs(
        consumerFactory: ConsumerFactory<String, V>,
        template: KafkaTemplate<String, V>
    ): Unit {
        this.setAutoStartup(true)
        this.setConcurrency(3)
        this.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        this.containerProperties.isAsyncAcks = false
        // asyncAck 모드가 true 이면 nack 을 못쓴다.
        this.consumerFactory = consumerFactory
        this.setReplyTemplate(template)
    }

    /** Consumer Factories */

    @Bean
    fun recentCurrenyInfoDtoConsumerFactory(): ConsumerFactory<String, TickInfoDto> =
        consumerFactory<TickInfoDto>()

    @Bean
    fun tickTxConsumerFactory(): ConsumerFactory<String, TickTxDto> =
        consumerFactory<TickTxDto>()

    private fun <V> consumerFactory(
        consumerProps: Map<String, Any> = defaultConsumerProps
    ): ConsumerFactory<String, V> {
        return DefaultKafkaConsumerFactory<String, V>(consumerProps)
    }

    /** Default Consumer properties */

    private val defaultConsumerProps: Map<String, Any> = kafkaProperties.buildConsumerProperties().apply {
        this[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = false
        this[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
        // this[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        this[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = ErrorHandlingDeserializer::class.java
        this[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = ErrorHandlingDeserializer::class.java
        this[ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS] = StringDeserializer::class.java
        this[ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS] = JsonDeserializer::class.java.name

        this[JsonDeserializer.TRUSTED_PACKAGES] = "*"

        this[ConsumerConfig.GROUP_ID_CONFIG] = CONSUMER_GROUP_ONE
    }
}

////////////////////////////////////////////////////////////////////////////
//    @Bean
//    fun reactiveKafkaConsumerTemplateForTickTxDto(): ReactiveKafkaConsumerTemplate<String, TickerTransactionDto> {
//        val receiverOptions =
//            receiverOptions<String, TickerTransactionDto>(groupId = "group-1", topicName = TICK_TX_TOPIC)
//        return ReactiveKafkaConsumerTemplate(receiverOptions)
//    }
//
//    @Bean
//    fun reactiveKafkaConsumerTemplateForRecentCurrenyInfoDto(): ReactiveKafkaConsumerTemplate<String, RecentCurrencyInfoDto> {
//        val receiverOptions =
//            receiverOptions<String, RecentCurrencyInfoDto>(groupId = "group-2", topicName = TICK_INFO_TOPIC)
//        return ReactiveKafkaConsumerTemplate(receiverOptions)
//    }
//    fun <K, V> receiverOptions(groupId: String, topicName: String): ReceiverOptions<K, V> {
//        val props: MutableMap<String, Any> = kafkaProperties.buildConsumerProperties()
//        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
//        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
//        props[ConsumerConfig.GROUP_ID_CONFIG] = groupId
//        props[ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG] = true
//
//        return ReceiverOptions.create<K, V>(props)
//            .subscription(Collections.singleton(topicName))
//    }
