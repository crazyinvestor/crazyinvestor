package net.crazyinvestor.engine_aaa.persistence

import net.crazyinvestor.dto.TickTxDto
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.CassandraType
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("ticker_transaction")
data class TickerTransaction (
    @PrimaryKeyColumn(value = "ticker_id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    @CassandraType(type = CassandraType.Name.UUID)
    val tickerId: UUID,

    // 통화 이름
    @Column("ticker_name")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val tickerName: String,

    // 체결 종류(1: 매도, 2: 매수)
    @PrimaryKeyColumn(value = "buy_sell_gb", type = PrimaryKeyType.PARTITIONED, ordinal = 2)
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val buySellGb: String,

    // 체결가격
    @Column("cont_price")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val contPrice: String,

    // 체결수량
    @Column("cont_qty")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val contQty: String,

    // 체결금액
    @Column("cont_amt")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val contAmt: String,

    // 체결시각
    @PrimaryKeyColumn(value = "cont_dtm", type = PrimaryKeyType.PARTITIONED, ordinal = 1)
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    val contDtm: LocalDateTime,

    // 직전 시세와 비교 (up: 상승, dn: 하락)
    @Column("updn")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val updn: String
) {
    companion object {
        fun fromDto(dto: TickTxDto): TickerTransaction = with(dto) {
            TickerTransaction(
                tickerId = tickerId,
                tickerName = tickerName,
                buySellGb = buySellGb,
                contPrice = contPrice,
                contAmt = contAmt,
                contQty = contQty,
                contDtm = contDtm,
                updn = updn
            )
        }
    }
}