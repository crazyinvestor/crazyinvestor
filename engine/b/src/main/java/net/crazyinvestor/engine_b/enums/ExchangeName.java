package net.crazyinvestor.engine_b.enums;

import lombok.Getter;

@Getter
public enum ExchangeName {
    BITHUMB("bithumb"),
    ;

    private final String exchangeName;

    ExchangeName(final String exchangeName) {
        this.exchangeName = exchangeName;
    }
}
