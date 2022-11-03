package net.crazyinvestor.ticker_producer_engine.entity

data class TickerHistory (
    val id: Long?,
    val ticker: Ticker?,
    val timestamp: Long?,
    val openPrice: Double?,
    val highPrice: Double?,
    val lowPrice: Double?,
    val closePrice: Double?,
    val volume: Double? ,
    val createdAt: Long?,
    val updatedAt: Long?,
)