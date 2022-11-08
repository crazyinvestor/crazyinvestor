package net.crazyinvestor.engine_aaa.persistence

import net.crazyinvestor.engine_aaa.dto.TickInfoDto
import org.springframework.data.cassandra.core.cql.Ordering
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.CassandraType
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("recent_currency_info")
data class RecentCurrencyInfo(

    // Ticker 고유 번호
    @PrimaryKeyColumn(
        value = "ticker_id",
        type = PrimaryKeyType.PARTITIONED,
        ordinal = 0
    )
    @CassandraType(
        type = CassandraType.Name.UUID
    )
    val tickerId: UUID,

    // 일자 YYYYMMDD
    @PrimaryKeyColumn(
        value = "date",
        type = PrimaryKeyType.PARTITIONED,
        ordinal = 1,
    )
    @CassandraType(
        type = CassandraType.Name.INT
    )
    val date: Int,

    // 시 (0~23)
    @PrimaryKeyColumn(
        value = "hour",
        type = PrimaryKeyType.PARTITIONED,
        ordinal = 2,
    )
    @CassandraType(
        type = CassandraType.Name.TINYINT
    )
    val hour: Int,

    // 요청 시간 HHMMSS
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    val timestamp: LocalDateTime,

    // 변동 기준 시간 (1H, 12H, 24H, ...)
    @PrimaryKeyColumn(
        value = "tick_type",
        type = PrimaryKeyType.PARTITIONED,
        ordinal = 3
    )
    @Column("tick_type")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val tickType: String,

    // 통화 코드 (BTC_KRW)
    @Column("ticker_name")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val tickerName: String,

    // 시가
    @Column("open_price")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val openPrice: String,

    // 고가
    @Column("high_price")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val highPrice: String,

    // 종가
    @Column("close_price")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val closePrice: String,

    // 요청 시간 YYYY:MM:DD HH:MM:SS MS
    @PrimaryKeyColumn(
        value = "created_at",
        type = PrimaryKeyType.CLUSTERED,
        ordinal = 4,
        ordering = Ordering.DESCENDING
    )
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    val createdAt: LocalDateTime,

    // 저가
    @Column("low_price")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val lowPrice: String,

    // 누적거래량
    @Column("volume")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val volume: String,

    // 누적거래금액
    @Column("value")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val value: String,

    // 매도누적거래량
    @Column("sell_volume")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val sellVolume: String,

    // 매수누적거래량
    @Column("buy_volume")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val buyVolume: String,

    // 전일종가
    @Column("prev_close_price")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val prevClosePrice: String,

    // 변동율
    @Column("chg_rate")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val chgRate: String,

    // 변동금액
    @Column("chg_amt")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val chgAmt: String,

    // 체결강도
    @Column("volume_power")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val volumePower: String,
){
    companion object {
        fun fromRecentCurrenyInfoDto(dto: TickInfoDto): RecentCurrencyInfo {
            return RecentCurrencyInfo(
                tickerId = dto.id!!,
                date = dto.date,
                hour = dto.hour,
                timestamp = dto.timestamp,
                tickType = dto.tickType,
                tickerName = dto.tickerName,
                openPrice = dto.openPrice,
                closePrice = dto.closePrice,
                highPrice = dto.highPrice,
                createdAt = dto.createdAt,
                lowPrice = dto.lowPrice,
                volume = dto.volume,
                buyVolume = dto.buyVolume,
                chgAmt = dto.chgAmt,
                chgRate = dto.chgRate,
                prevClosePrice = dto.prevClosePrice,
                sellVolume = dto.sellVolume,
                value = dto.value,
                volumePower = dto.volumePower
            )
        }
    }
}