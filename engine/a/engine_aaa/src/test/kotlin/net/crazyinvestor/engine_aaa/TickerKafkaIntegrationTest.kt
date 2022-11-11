package net.crazyinvestor.engine_aaa

import com.datastax.oss.driver.api.core.uuid.Uuids
// import net.crazyinvestor.engine_aaa.constants.NEW_TICKER_TOPIC
import net.crazyinvestor.engine_aaa.persistence.Ticker
import org.apache.kafka.clients.producer.*
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("dev")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class TickerKafkaIntegrationTest {
    lateinit var producerFactory: ProducerFactory<String, Ticker>
    lateinit var producer: Producer<String, Ticker>

    @BeforeAll
    fun beforeAll(): Unit { producerFactory = DefaultKafkaProducerFactory<String, Ticker>(PRODUCER_CONFIGS) }

    @BeforeEach
    fun setUp() { producer = producerFactory.createProducer() }

    @AfterEach
    fun tearDown() { producer.close() }

    @AfterAll
    fun afterAll() { producerFactory.closeThreadBoundProducer() }

    @Test
    fun `can send valid input`() {
        val callback: Callback =
            Callback { metadata, exception -> println(metadata to exception) }

        val metadata = producer.send(ProducerRecord("new-ticker", VALID_TICKER), callback)
            .get()

        println(metadata)
    }

    @Test
    fun `can send more tickers`() {
        val callback: Callback =
            Callback { metadata, exception -> println(metadata to exception) }

        repeat(100){
            producer.send(ProducerRecord("new-ticker", getRandomTicker()), callback).get()
        }
    }

    companion object {
        val VALID_TICKER = Ticker(Uuids.timeBased(), "ticker-test-123")
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
