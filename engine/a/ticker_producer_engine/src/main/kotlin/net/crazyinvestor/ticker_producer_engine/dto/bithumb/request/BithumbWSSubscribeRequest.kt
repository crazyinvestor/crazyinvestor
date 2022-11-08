package net.crazyinvestor.ticker_producer_engine.dto.bithumb.request

import net.crazyinvestor.ticker_producer_engine.enums.BithumbOpType
import java.io.Serializable

class BithumbWSSubscribeRequest(
    private val opType: BithumbOpType,
    private val symbols: List<String>,
    private val tickTypes: List<String>
) : Serializable {
    override fun toString(): String {
        fun String.quoted(): String = "\"${this}\""

//        val joinedStringForSymbols: String = symbols
//            .map(BithumbSymbol::type)
//            .distinct()
//            .joinToString(transform = String::quoted)
        val joinedStringForSymbols: String = symbols
            .map { "${it}_KRW" }
            .distinct()
            .joinToString(transform = String::quoted)

//        val joinedStringForTickTypes: String = tickTypes
//            .map(BithumbTickTypes::tickType)
        val joinedStringForTickTypes: String = tickTypes
            .distinct()
            .joinToString(transform = String::quoted)

        return "{ ${"type".quoted()} : ${opType.opType.quoted()}, ${"symbols".quoted()} : [$joinedStringForSymbols], ${"tickTypes".quoted()} : [$joinedStringForTickTypes]}"
    }

    companion object {
        fun fromOpType(opType: BithumbOpType, symbols: List<String>, tickTypes: List<String>): BithumbWSSubscribeRequest {
            return BithumbWSSubscribeRequest(
                opType = opType,
                symbols = symbols,
                tickTypes = tickTypes
            )
        }
    }
}