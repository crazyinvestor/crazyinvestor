package net.crazyinvestor.ticker_producer_engine.dto.request

import net.crazyinvestor.ticker_producer_engine.enums.BithumbOpType
import net.crazyinvestor.ticker_producer_engine.enums.BithumbSymbol
import net.crazyinvestor.ticker_producer_engine.enums.BithumbTickTypes
import java.io.Serializable

class BithumbWSSubscribeRequest(
    private val opType: BithumbOpType,
    private val symbols: List<BithumbSymbol>,
    private val tickTypes: List<BithumbTickTypes>
) : Serializable {
    override fun toString(): String {
        fun String.quoted(): String = "\"${this}\""

        val joinedStringForSymbols: String = symbols
            .map(BithumbSymbol::type)
            .distinct()
            .joinToString(transform = String::quoted)

        val joinedStringForTickTypes: String = tickTypes
            .map(BithumbTickTypes::tickType)
            .distinct()
            .joinToString(transform = String::quoted)

        return "{ ${"type".quoted()} : ${opType.opType.quoted()}, ${"symbols".quoted()} : [$joinedStringForSymbols], ${"tickTypes".quoted()} : [$joinedStringForTickTypes]}"
    }

    companion object {
        fun fromOpType(opType: BithumbOpType, symbols: List<BithumbSymbol>, tickTypes: List<BithumbTickTypes>): BithumbWSSubscribeRequest {
            return BithumbWSSubscribeRequest(
                opType = opType,
                symbols = symbols,
                tickTypes = tickTypes
            )
        }
    }
}