package net.crazyinvestor.engine_b.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BithumbOpType {
    TICKER("ticker"),
    TRANSACTION("transaction"),
    ORDERBOOK_DEPTH("orderbookdepth"),
    ;

    private final String opType;
}
