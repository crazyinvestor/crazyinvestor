package net.crazyinvestor.ticker_producer_engine.dto.response

class BithumbWSStatusResponse (
    val status: String?,
    val resmsg: String?
){
    constructor(): this(null, null){}
}