package net.crazyinvestor.ticker_producer_engine.hadler

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import net.crazyinvestor.ticker_producer_engine.dto.bithumb.response.BithumbWSOrderbookDepthResponse
import net.crazyinvestor.ticker_producer_engine.dto.bithumb.response.BithumbWSTickerResponse
import net.crazyinvestor.ticker_producer_engine.dto.bithumb.response.BithumbWSTransactionResponse
import net.crazyinvestor.ticker_producer_engine.dto.bithumb.response.BithumbWSTransactionResponseContent
import net.crazyinvestor.ticker_producer_engine.enums.BithumbOpType
import net.crazyinvestor.ticker_producer_engine.service.TickerOpService
import net.crazyinvestor.ticker_producer_engine.service.TickerTxOpService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.util.*

@Component
class BithumbWSResponseHandler(
    private val tickerService: TickerOpService,
    private val tickerTxOpService: TickerTxOpService,
    private val eventPublisher: ApplicationEventPublisher
) {
    private val objectMapper: ObjectMapper = ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    private fun readTreeMono(data: String?): Mono<JsonNode> {
        return data.toMono()
            .map { objectMapper.readTree(it) }
            .doOnError { it.printStackTrace(); System.exit(1) }
    }

    fun handle(data: String?) {
        readTreeMono(data)
            .doOnNext { response ->
                when {
                    Objects.nonNull(response["status"]) -> handleStatusMessage(response)
                    else -> when(response["type"].asText()) {
                        BithumbOpType.TICKER.opType -> handleTickerData(response)
                        BithumbOpType.TRANSACTION.opType -> handleTransactionData(response)
                        BithumbOpType.ORDERBOOK_DEPTH.opType -> handleOrderbookDepthData(response)
                        else -> { println("NOT EXISTS TYPE"); }
                    }
                }
            }
            .subscribe()
    }
//        try {
//            val response = objectMapper.readTree(data)
//            when {
//                Objects.nonNull(response["status"]) -> handleStatusMessage(response)
//                else -> when(response["type"].asText()) {
//                    BithumbOpType.TICKER.opType -> handleTickerData(response)
//                    BithumbOpType.TRANSACTION.opType -> handleTransactionData(response)
//                    BithumbOpType.ORDERBOOK_DEPTH.opType -> handleOrderbookDepthData(response)
//                    else -> { println("NOT EXISTS TYPE"); }
//                }
//            }
//        } catch (e: JsonProcessingException) {
//            e.printStackTrace()
//            System.exit(1)
//        }

    @Throws(JsonProcessingException::class)
    private fun handleOrderbookDepthData(response: JsonNode) {
        val type = response["type"].asText()
        val content = response["content"]
        val datetime = content["datetime"].asLong()
        val responseObj: BithumbWSOrderbookDepthResponse = objectMapper.treeToValue<BithumbWSOrderbookDepthResponse>(
            content,
            BithumbWSOrderbookDepthResponse::class.java
        )
        responseObj.datetime = datetime
        // eventPublisher.publishEvent(BithumbOrderbookDepthEvent(responseObj))

        // TODO: OrderbookDepth 저장 로직
    }

    @Throws(JsonProcessingException::class)
    private fun handleTransactionData(response: JsonNode) {
        val type = response["type"].asText()
        val content = response["content"]
        val responseObj: BithumbWSTransactionResponse = objectMapper.treeToValue<BithumbWSTransactionResponse>(
            content,
            BithumbWSTransactionResponse::class.java
        )
        responseObj.list!!
            .toFlux()
            .flatMap { Mono.just(it) }
            .doOnNext {
                tickerTxOpService.produce(it)
            }
            .subscribe()
    }

    private fun handleTickerData(response: JsonNode) {
        // val type = response["type"].asText()
        val content = response["content"]
        val responseObj: BithumbWSTickerResponse = objectMapper.treeToValue<BithumbWSTickerResponse>(
            content,
            BithumbWSTickerResponse::class.java
        )
        tickerService.saveBithumbTicker(responseObj)
    }

    private fun handleStatusMessage(response: JsonNode) {
        val status = response["status"].asText()
        val resmsg = response["resmsg"].asText()
        println("Status response = $response")
    }
}
