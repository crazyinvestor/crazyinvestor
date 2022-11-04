package net.crazyinvestor.engine_aaa.persistence

import net.crazyinvestor.engine_aaa.dto.RecentCurrencyInfoDto
import org.springframework.data.cassandra.core.cql.Ordering
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.CassandraType
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import java.time.LocalDateTime
import java.util.*
import kotlin.reflect.jvm.internal.impl.types.AbstractStubType.Companion

@Table("recent_currency_info")
data class RecentCurrencyInfo(
    //  TODO 시(hour) 를 Primary(2) 키로 넣을까?

    @PrimaryKeyColumn(
        value = "ticker_id",
        type = PrimaryKeyType.PARTITIONED,
        ordinal = 0
    )
    @CassandraType(
        type = CassandraType.Name.UUID
    )
    val tickerId: UUID,

    @PrimaryKeyColumn(
        value = "date",
        type = PrimaryKeyType.PARTITIONED,
        ordinal = 1,
        ordering = Ordering.DESCENDING
    )
    @CassandraType(
        type = CassandraType.Name.INT
    )
    val date: Int,

    @PrimaryKeyColumn(
        value = "hour",
        type = PrimaryKeyType.PARTITIONED,
        ordinal = 2,
        ordering = Ordering.DESCENDING
    )
    @CassandraType(
        type = CassandraType.Name.TINYINT
    )
    val hour: Int,

    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    val timestamp: LocalDateTime,

    @Column("tick_type")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val tickType: String,

    @Column("tick_name")
    val tickerName: String,

    @Column("open_price")
    val openPrice: String,

    @Column("high_price")
    val highPrice: String,

    @Column("close_price")
    val closePrice: String,

    @PrimaryKeyColumn(
        value = "created_at",
        type = PrimaryKeyType.CLUSTERED,
        ordinal = 3,
        ordering = Ordering.DESCENDING
    )
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    val createdAt: LocalDateTime,

    @Column("low_price")
    val lowPrice: String,

    @Column("volume")
    val volume: String
){
    companion object {
        fun fromRecentCurrenyInfoDto(dto: RecentCurrencyInfoDto): RecentCurrencyInfo {
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
                volume = dto.volume
            )
        }
    }
}