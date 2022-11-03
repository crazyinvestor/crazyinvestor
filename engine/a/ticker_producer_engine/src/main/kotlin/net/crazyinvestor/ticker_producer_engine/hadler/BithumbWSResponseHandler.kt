package net.crazyinvestor.ticker_producer_engine.hadler

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import net.crazyinvestor.ticker_producer_engine.dto.response.BithumbWSOrderbookDepthResponse
import net.crazyinvestor.ticker_producer_engine.dto.response.BithumbWSTickerResponse
import net.crazyinvestor.ticker_producer_engine.dto.response.BithumbWSTransactionResponse
import net.crazyinvestor.ticker_producer_engine.enums.BithumbOpType
import net.crazyinvestor.ticker_producer_engine.event.BithumbOrderbookDepthEvent
import net.crazyinvestor.ticker_producer_engine.event.BithumbTickerEvent
import net.crazyinvestor.ticker_producer_engine.event.BithumbTransactionEvent
import net.crazyinvestor.ticker_producer_engine.service.TickerService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import java.util.*

@Component
class BithumbWSResponseHandler(
    private val tickerService: TickerService,
    private val eventPublisher: ApplicationEventPublisher
) {
    private val objectMapper: ObjectMapper = ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun handle(data: String?) {
        try {
            val response = objectMapper.readTree(data)
            if (Objects.nonNull(response["status"])) {
                handleStatusMessage(response)
            } else {
                val type = response["type"].asText()
                if (BithumbOpType.TICKER.opType == type) {
                    handleTickerData(response)
                } else if (BithumbOpType.TRANSACTION.opType == type) {
                    handleTransactionData(response)
                } else if (BithumbOpType.ORDERBOOK_DEPTH.opType == type) {
                    handleOrderbookDepthData(response)
                }
            }
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
            System.exit(1)
        }
    }

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
        eventPublisher.publishEvent(BithumbOrderbookDepthEvent(responseObj))

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
        eventPublisher.publishEvent(BithumbTransactionEvent(responseObj))

        // TODO: Transaction 저장 로직
    }

    @Throws(JsonProcessingException::class)
    private fun handleTickerData(response: JsonNode) {
        val type = response["type"].asText()
        val content = response["content"]
        val responseObj: BithumbWSTickerResponse = objectMapper.treeToValue<BithumbWSTickerResponse>(
            content,
            BithumbWSTickerResponse::class.java
        )
        eventPublisher.publishEvent(BithumbTickerEvent(responseObj))
        tickerService.saveBithumbTicker(responseObj)
    }

    private fun handleStatusMessage(response: JsonNode) {
        val status = response["status"].asText()
        val resmsg = response["resmsg"].asText()
        println("Status response = $response")
    }
}
