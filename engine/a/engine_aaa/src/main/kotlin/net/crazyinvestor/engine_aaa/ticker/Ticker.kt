package net.crazyinvestor.engine_aaa.ticker

import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

@Table
class Ticker(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val quantity: Int = 0
)
