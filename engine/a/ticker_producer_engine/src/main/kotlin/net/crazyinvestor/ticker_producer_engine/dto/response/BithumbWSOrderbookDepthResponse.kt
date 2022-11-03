package net.crazyinvestor.ticker_producer_engine.dto.response

class BithumbWSOrderbookDepthResponse (
    val list: List<BithumbWSOrderbookDepthResponseContent>?,
    var datetime: Long?,
){
    constructor(): this(null, null){}
}