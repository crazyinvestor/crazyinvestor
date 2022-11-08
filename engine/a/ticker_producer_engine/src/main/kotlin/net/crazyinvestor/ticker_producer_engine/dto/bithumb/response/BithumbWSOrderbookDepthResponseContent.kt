package net.crazyinvestor.ticker_producer_engine.dto.bithumb.response

class BithumbWSOrderbookDepthResponseContent (
    val symbol: String?,
    val orderType: String?,
    val price: String?,
    val quantity: String?,
    val total: String?
){
    constructor(): this(null, null, null, null, null){}
}