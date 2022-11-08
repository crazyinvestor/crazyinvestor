package net.crazyinvestor.ticker_producer_engine.dto.bithumb.response

class BithumbWSOrderbookDepthResponse (
    val list: List<BithumbWSOrderbookDepthResponseContent>?,
    var datetime: Long?,
){
    constructor(): this(null, null){}
}