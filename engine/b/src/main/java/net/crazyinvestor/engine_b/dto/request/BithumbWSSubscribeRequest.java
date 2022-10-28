package net.crazyinvestor.engine_b.dto.request;

import lombok.Builder;
import net.crazyinvestor.engine_b.enums.BithumbOpType;
import net.crazyinvestor.engine_b.enums.BithumbSymbol;
import net.crazyinvestor.engine_b.enums.BithumbTickTypes;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Builder
public final class BithumbWSSubscribeRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -687691491344005033L;

    private final BithumbOpType opType;
    private final List<BithumbSymbol> symbols;
    private final List<BithumbTickTypes> tickTypes;

    @Override
    public String toString() {
        List<String> symbolStrList = this.symbols
                .stream()
                .map(BithumbSymbol::getType)
                .distinct() // 중복 제거
                .map(v -> "\"" + v + "\"") // 문자열 가공
                .toList(); // 리스트화

        List<String> tickTypeStrList = this.tickTypes
                .stream()
                .map(BithumbTickTypes::getTickType)
                .distinct() // 중복 제거
                .map(v -> "\"" + v + "\"") // 문자열 가공
                .toList(); // 리스트화

        return "{" +
                "\"type\" : \"" + this.opType.getOpType() + "\"" + ", " +
                "\"symbols\" : " + "[" + String.join(", ", symbolStrList) + "]" + ", " +
                "\"tickTypes\" : " + "[" + String.join(", ", tickTypeStrList) + "]" +
                "}";
    }
}
