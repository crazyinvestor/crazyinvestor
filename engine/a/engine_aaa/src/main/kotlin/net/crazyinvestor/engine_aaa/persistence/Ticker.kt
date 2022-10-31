package net.crazyinvestor.engine_aaa.persistence

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.util.UUID
//import com.fasterxml.jackson.annotation.JsonIgnore
//import org.springframework.data.cassandra.core.mapping.Column
//import org.springframework.data.cassandra.core.mapping.CassandraType

@JsonDeserialize
@Table
public data class Ticker (
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @PrimaryKey("uuid")
    public var uuid: UUID,

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public var name: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public var quantity: Int = 0
)

//@JsonDeserialize
//@Table
//public data class TickerTable (
//    @field:PrimaryKey("name")
//    public var name: String,
//
//    @field:PrimaryKey("timestamp")
//    public var timestamp: Timestamp,
//
//
//    @JsonFormat(shape = JsonFormat.Shape.STRING)
//    @Column("quantity")
//    public var quantity: Int = 0
//)
