package net.crazyinvestor.dto

import java.time.LocalDateTime

class TickerOrderDto (
    // 통화 이름

    val tickerName: String,

    // 객체 생성 시간

    val createdAt: LocalDateTime,

    // 주문 시간

    val datetime: String,

    // 주문 타입 - bid / ask

    val orderType: String,

    // 호가

    val price: String,

    // 잔량

    val quantity: String,

    // 건수

    val total: Int,
)
