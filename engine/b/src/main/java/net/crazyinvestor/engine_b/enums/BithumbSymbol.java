package net.crazyinvestor.engine_b.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BithumbSymbol {
    BTC_KRW("BTC_KRW"),
    ETH_KRW("ETH_KRW"),
    ;

    private final String type;
}
