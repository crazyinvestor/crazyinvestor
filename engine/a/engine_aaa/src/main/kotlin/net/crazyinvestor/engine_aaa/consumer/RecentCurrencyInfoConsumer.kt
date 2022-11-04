package net.crazyinvestor.engine_aaa.consumer

import net.crazyinvestor.engine_aaa.dto.RecentCurrencyInfoDto
import net.crazyinvestor.engine_aaa.persistence.RecentCurrencyInfo
import net.crazyinvestor.engine_aaa.persistence.Ticker
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.reactivestreams.Publisher
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate
import org.springframework.data.cassandra.core.convert.Where
import org.springframework.data.cassandra.core.query.Criteria
import org.springframework.data.cassandra.core.query.Query
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import org.springframework.stereotype.Component
import reactor.util.Loggers

@Component
class RecentCurrencyInfoConsumer(
    val reactiveKafkaConsumerTemplate: ReactiveKafkaConsumerTemplate<String, RecentCurrencyInfoDto>,
    val reactiveCassandraTemplate: ReactiveCassandraTemplate
) : ApplicationRunner {
    companion object {
        val logger = Loggers.getLogger(RecentCurrencyInfoConsumer::class.java)
    }

    override fun run(args: ApplicationArguments?) {
        reactiveKafkaConsumerTemplate
            .receiveAutoAck()
            .concatMap(getConsumeFunction())
            .subscribe()
    }

    private fun getConsumeFunction(): (t: ConsumerRecord<String, RecentCurrencyInfoDto>) -> Publisher<out RecentCurrencyInfo> = lambda@ {
        logger.debug("CONSUME id=${it.value().id}, tickerName=${it.value().tickerName}, closePrice=${it.value().closePrice}")
        val tickerDto = it.value()

        if(tickerDto.id != null){
            return@lambda reactiveCassandraTemplate
                .insert(RecentCurrencyInfo.fromRecentCurrenyInfoDto(tickerDto))
        }

        // TODO 디비에 없는 ticker 이름이 들어온 경우?
        TODO()
        // CASE 클라이언트가 ticker 의 이름만 알고 ID 를 모르는 경우
        val query: Query = Query.query(Criteria.where("ticker_name").`is`(tickerDto.tickerName))
        reactiveCassandraTemplate
            .selectOne(query, RecentCurrencyInfo::class.java)
            .map { it.tickerId }
            .flatMap { id ->
                reactiveCassandraTemplate
                    .insert(RecentCurrencyInfo.fromRecentCurrenyInfoDto(tickerDto.copy(id = id)))
            }
    }
}
