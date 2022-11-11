package net.crazyinvestor.engine_aaa.persistence

import net.crazyinvestor.dto.TickerDto
import org.springframework.data.cassandra.core.mapping.CassandraType
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.util.*

@Table
public data class Ticker (
    @CassandraType(type = CassandraType.Name.UUID)
    @PrimaryKey("id")
    var id: UUID,

    @CassandraType(type = CassandraType.Name.VARCHAR)
    var name: String
){
    companion object {
        fun fromTickerDto(dto: TickerDto): Ticker{
            return Ticker(
                id = dto.id!!,
                name = dto.name
            )
        }
    }
}