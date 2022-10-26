package net.crazyinvestor.engine_b.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.crazyinvestor.engine_b.dto.response.BithumbWSOrderbookDepthResponse;
import net.crazyinvestor.engine_b.dto.response.BithumbWSTickerResponse;
import net.crazyinvestor.engine_b.dto.response.BithumbWSTransactionResponse;
import net.crazyinvestor.engine_b.enums.BithumbOpType;
import net.crazyinvestor.engine_b.service.TickerService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class BithumbWSResponseHandler {
    private final TickerService tickerService;
    private final ObjectMapper objectMapper;

    public BithumbWSResponseHandler(
            final TickerService tickerService
    ) {
        this.tickerService = tickerService;

        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void handle(String data) {
        try {
            JsonNode response = objectMapper.readTree(data);

            if (Objects.nonNull(response.get("status"))) {
                handleStatusMessage(response);
            } else {
                String type = response.get("type").asText();

                if (BithumbOpType.TICKER.getOpType().equals(type)) {
                    handleTickerData(response);
                } else if (BithumbOpType.TRANSACTION.getOpType().equals(type)) {
                    handleTransactionData(response);
                } else if (BithumbOpType.ORDERBOOK_DEPTH.getOpType().equals(type)) {
                    handleOrderbookDepthData(response);
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void handleOrderbookDepthData(JsonNode response) throws JsonProcessingException {
        String type = response.get("type").asText();
        JsonNode content = response.get("content");
        Long datetime = content.get("datetime").asLong();

        BithumbWSOrderbookDepthResponse bithumbWSOrderbookDepthResponse = objectMapper.treeToValue(content, BithumbWSOrderbookDepthResponse.class);
        bithumbWSOrderbookDepthResponse.setDatetime(datetime);

        System.out.println("Orderbook response = " + response);
    }

    private void handleTransactionData(JsonNode response) throws JsonProcessingException {
        String type = response.get("type").asText();
        JsonNode content = response.get("content");

        BithumbWSTransactionResponse bithumbWSTransactionResponse = objectMapper.treeToValue(content, BithumbWSTransactionResponse.class);

        System.out.println("Transaction response = " + response);
    }

    private void handleTickerData(JsonNode response) throws JsonProcessingException {
        String type = response.get("type").asText();
        JsonNode content = response.get("content");

        BithumbWSTickerResponse bithumbWSTickerResponse = objectMapper.treeToValue(content, BithumbWSTickerResponse.class);
        tickerService.saveBithumbTicker(bithumbWSTickerResponse);

//        System.out.println("Ticker response = " + response);
    }

    private void handleStatusMessage(JsonNode response) {
        String status = response.get("status").asText();
        String resmsg = response.get("resmsg").asText();

        System.out.println("Status response = " + response);
    }
}
