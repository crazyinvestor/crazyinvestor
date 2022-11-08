package net.crazyinvestor.ticker_producer_engine.enums

enum class BithumbTickTypes(val tickType: String) {
    MIN_30("30M"),
    HR_1("1H"),
    HR_12("12H"),
    HR_24("24H"),
    MID("MID")
    ;
}

val TICK_TYPES = listOf("30M", "1H", "12H", "24H", "MID")