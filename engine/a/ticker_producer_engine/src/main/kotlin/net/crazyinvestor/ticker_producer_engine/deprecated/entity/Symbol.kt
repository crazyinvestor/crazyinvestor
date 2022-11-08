package net.crazyinvestor.ticker_producer_engine.deprecated.entity

data class Symbol (
    val id: Long?,
    val name: String?,
    val exchange: Exchange?,
    val quote: Quote?,
)