package net.crazyinvestor.engine_aaa.persistence

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.cassandra.core.mapping.CassandraType
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.util.UUID

@Table
data class Exchange(
    @CassandraType(type = CassandraType.Name.UUID)
    @PrimaryKey("id")
    val id: UUID,

    @CassandraType(type = CassandraType.Name.VARCHAR)
    val name: String
)