package net.crazyinvestor.engine_aaa.tmp
//
//import kotlinx.coroutines.reactor.awaitSingle
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.RestController
//
//import org.springframework.kafka.annotation.KafkaListener
//import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
//import org.springframework.kafka.support.KafkaHeaders
//import org.springframework.messaging.handler.annotation.Header

//@RestController
class FooController
//    (
//    val tickerService: TickerServiceImpl
//)
//{
////    @GetMapping("/foo/cassandra-test")
////    suspend fun foo_checkCassandra(): Ticker {
////        return tickerService
////            .foo_createTicker()
////            .awaitSingle()
////    }
//
//}
/////////////
// Producer
//    @Bean
//    fun runner(template: ReactiveKafkaProducerTemplate<String?, String?>) =
//        ApplicationRunner { template.send("topic1", test) }
//@Service
//class TickerServiceRand(
//    private val tickerRepository: TickerRepository
//): TickerService {
//    override fun createTicker(value: String): Mono<Ticker> {
//        val ticker = Ticker(
//            id = "${fooRand()}",
//            name = "${fooRand()}",
//            quantity = fooRand()
//        )
//
//        val saved = tickerRepository.save(ticker)
//
//        return saved
//    }
//    val fooRand: () -> Int = { (Math.random()*10000).toInt() }
//}