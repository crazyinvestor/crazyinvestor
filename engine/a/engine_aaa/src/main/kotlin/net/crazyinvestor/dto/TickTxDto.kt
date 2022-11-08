package net.crazyinvestor.dto

import java.time.LocalDateTime
import java.util.UUID

data class TickTxDto (

    val tickerId: UUID,

    // 통화 이름

    val tickerName: String,

    // 체결 종류(1: 매도, 2: 매수)

    val buySellGb: String,

    // 체결가격

    val contPrice: String,

    // 체결수량

    val contQty: String,

    // 체결금액

   val contAmt: String,

    // 체결시각

    val contDtm: LocalDateTime,

    // 직전 시세와 비교 (up: 상승, dn: 하락)

    val updn: String
)