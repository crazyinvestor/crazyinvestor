package net.crazyinvestor.engine_b.dto;

import net.crazyinvestor.engine_b.dto.request.BithumbWSSubscribeRequest;
import net.crazyinvestor.engine_b.enums.BithumbOpType;
import net.crazyinvestor.engine_b.enums.BithumbSymbol;
import net.crazyinvestor.engine_b.enums.BithumbTickTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

class BithumbWSSubscribeRequestTest {

    @Test
    void SerializeTest() {
        final BithumbOpType opType = BithumbOpType.TICKER;

        final List<BithumbSymbol> symbols = new LinkedList<>();
        symbols.add(BithumbSymbol.BTC_KRW);
        symbols.add(BithumbSymbol.ETH_KRW);

        final List<BithumbTickTypes> tickTypes = new LinkedList<>();
        tickTypes.add(BithumbTickTypes.HR_1);

        final BithumbWSSubscribeRequest request = new BithumbWSSubscribeRequest(opType, symbols, tickTypes);
        System.out.println(request);

        Assertions.assertEquals(
                "{\"type\" : \"ticker\", \"symbols\" : [\"BTC_KRW\", \"ETH_KRW\"], \"tickTypes\" : [\"1H\"]}",
                request.toString()
        );
    }
}