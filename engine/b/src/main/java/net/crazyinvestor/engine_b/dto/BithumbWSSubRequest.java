package net.crazyinvestor.engine_b.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public final class BithumbWSSubRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -687691491344005033L;

    private final String opType;
    private final List<String> symbols;
    private final List<String> tickTypes;

    public BithumbWSSubRequest(
            final BithumbOpType opType,
            final List<BithumbSymbol> symbols,
            final List<BithumbTickTypes> tickTypes
    ) {
        this.opType = opType.getOpType();

        this.symbols = symbols.stream()
                .map(BithumbSymbol::getType)
                .toList();

        this.tickTypes = tickTypes.stream()
                .map(BithumbTickTypes::getTickType)
                .toList();
    }

    @Override
    public String toString() {
        List<String> symbolStrList = this.symbols
                .stream()
                .distinct() // 중복 제거
                .map(v -> "\"" + v + "\"") // 문자열 가공
                .toList(); // 리스트화

        List<String> tickTypeStrList = this.tickTypes
                .stream()
                .distinct() // 중복 제거
                .map(v -> "\"" + v + "\"") // 문자열 가공
                .toList(); // 리스트화

        return "{" +
                "\"type\" : \"" + this.opType + "\"" + ", " +
                "\"symbols\" : " + "[" + String.join(", ", symbolStrList) + "]" + ", " +
                "\"tickTypes\" : " + "[" + String.join(", ", tickTypeStrList) + "]" +
                "}";
    }
}
