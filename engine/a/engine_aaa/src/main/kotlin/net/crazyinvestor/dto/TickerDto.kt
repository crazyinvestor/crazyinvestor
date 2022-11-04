package net.crazyinvestor.dto

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.springframework.data.cassandra.core.mapping.CassandraType
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.util.*

data class TickerDto(
    var id: UUID?,
    var name: String
)
