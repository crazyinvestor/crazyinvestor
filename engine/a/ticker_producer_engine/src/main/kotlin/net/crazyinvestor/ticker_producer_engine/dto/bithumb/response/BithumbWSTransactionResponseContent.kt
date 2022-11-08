package net.crazyinvestor.ticker_producer_engine.dto.bithumb.response

class BithumbWSTransactionResponseContent (
    val symbol: String?,
    val buySellGb: String?,
    val contPrice: String?,
    val contQty: String?,
    val contAmt: String?,
    val contDtm: String?,
    val updn: String?
){
    constructor(): this(null,null,null,null,null,null,null){}
}