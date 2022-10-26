package net.crazyinvestor.engine_b.enums;

import lombok.Getter;

@Getter
public enum Exchange {
    BITHUMB("bithumb"),
    ;

    private final String exchangeName;

    Exchange(final String exchangeName) {
        this.exchangeName = exchangeName;
    }
}
