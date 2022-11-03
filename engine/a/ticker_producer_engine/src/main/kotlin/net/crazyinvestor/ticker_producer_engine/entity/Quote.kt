package net.crazyinvestor.ticker_producer_engine.entity

data class Quote (
    var id: Long?,
    var symbol: Symbol?,
    var price: Double?,
    var createdAt: Long?,
    var updatedAt: Long?,
)