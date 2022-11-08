package net.crazyinvestor.ticker_producer_engine.dto.bithumb.response

class BithumbWSTransactionResponse (
    val list: List<BithumbWSTransactionResponseContent>?
){
    constructor(): this(null){}
}