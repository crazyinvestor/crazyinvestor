package net.crazyinvestor.engine_aaa

import com.datastax.oss.driver.api.core.CqlSessionBuilder
import com.datastax.oss.driver.api.core.uuid.Uuids
// import net.crazyinvestor.engine_aaa.constants.NEW_TICKER_TOPIC
import net.crazyinvestor.engine_aaa.persistence.Ticker
import org.apache.kafka.clients.producer.Callback
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.jupiter.api.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.cassandra.core.CassandraTemplate
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer
import org.springframework.test.context.ActiveProfiles

class Ticker {

}

@ActiveProfiles("dev")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class TickerConsumerKafkaCassandraIntegrationTest {
    lateinit var cassandraTemplate: CassandraTemplate
    lateinit var producerFactory: ProducerFactory<String, Ticker>
    lateinit var producer: Producer<String, Ticker>

    @BeforeAll
    fun beforeAll(): Unit {
        producerFactory = DefaultKafkaProducerFactory<String, Ticker>(PRODUCER_CONFIGS)
        cassandraTemplate = CassandraTemplate(CqlSessionBuilder().withKeyspace("djdb").build())
    }

    @BeforeEach
    fun setUp() { producer = producerFactory.createProducer() }

    @AfterEach
    fun tearDown() { producer.close() }

    @AfterAll
    fun afterAll() { producerFactory.closeThreadBoundProducer() }

    @Test
    fun `if produce 100 data, must stored 100 data into cassandra`() {
        val expected = 100L
         val initialCount: Long = cassandraTemplate.count(Ticker::class.java)

        val callback: Callback =
            Callback { metadata, exception -> println(metadata to exception) }

        repeat(expected.toInt()){
            producer.send(ProducerRecord("new-ticker", getRandomTicker()), callback).get()
        }

        println("sleep 7s")
        Thread.sleep(7000)

        val afterCount = cassandraTemplate
            .count(Ticker::class.java)

        Assertions.assertEquals(expected, afterCount - initialCount)

        repeat(10) { println() }
        println("success")
    }

    companion object {
        val PRODUCER_CONFIGS = mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:29092",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
        )
        fun getRandomTicker(): Ticker {
            return Ticker(Uuids.timeBased(),"ticker-${(Math.random()*10000).toInt()}")
        }
    }
}
