package net.crazyinvestor.ticker_producer_engine.enums

enum class BithumbOpType(val opType: String) {
    TICKER("ticker"),
    TRANSACTION("transaction"),
    ORDERBOOK_DEPTH("orderbookdepth")
    ;
}