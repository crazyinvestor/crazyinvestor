package net.crazyinvestor.ticker_producer_engine.dto.response

class BithumbWSTickerResponse (
    val symbol: String?,
    val tickType: String?,
    val date: String?,
    val time: String?,
    val openPrice: String?,
    val closePrice: String?,
    val lowPrice: String?,
    val highPrice: String?,
    val value: String?,
    val volume: String?,
    val sellVolume: String?,
    val buyVolume: String?,
    val prevClosePrice: String?,
    val chgRate: String?,
    val chgAmt: String?,
    val volumePower: String?
){
    constructor(): this(null, null, null, null, null,null,
        null, null,null, null, null,null, null, null,
        null, null
    )
}