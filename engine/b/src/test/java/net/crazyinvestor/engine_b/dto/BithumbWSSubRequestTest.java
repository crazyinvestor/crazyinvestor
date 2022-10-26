package net.crazyinvestor.engine_b.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

class BithumbWSSubRequestTest {

    @Test
    void SerializeTest() {
        final BithumbOpType opType = BithumbOpType.TICKER;

        final List<BithumbSymbol> symbols = new LinkedList<>();
        symbols.add(BithumbSymbol.BTC_KRW);
        symbols.add(BithumbSymbol.ETH_KRW);

        final List<BithumbTickTypes> tickTypes = new LinkedList<>();
        tickTypes.add(BithumbTickTypes.HR_1);

        final BithumbWSSubRequest request = new BithumbWSSubRequest(opType, symbols, tickTypes);
        System.out.println(request);

        Assertions.assertEquals(
                "{\"type\" : \"ticker\", \"symbols\" : [\"BTC_KRW\", \"ETH_KRW\"], \"tickTypes\" : [\"1H\"]}",
                request.toString()
        );
    }
}