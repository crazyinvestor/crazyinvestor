package net.crazyinvestor.engine_aaa.dto

import java.time.LocalDateTime
import java.util.UUID

data class TickInfoDto(
    val id: UUID?,
    val tickerName: String,
    val tickType: String,
    val timestamp: LocalDateTime,
    val openPrice: String,
    val highPrice: String,
    val closePrice: String,
    val createdAt: LocalDateTime,
    val date: Int,

    val lowPrice: String,
    val volume: String,
    val hour: Int,
    val value: String,
    val sellVolume: String,
    val buyVolume: String,
    val prevClosePrice: String,
    val chgRate: String,
    val chgAmt: String,
    val volumePower: String,
)