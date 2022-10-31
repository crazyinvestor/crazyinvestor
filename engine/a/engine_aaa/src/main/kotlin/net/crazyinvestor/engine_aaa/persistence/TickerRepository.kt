package net.crazyinvestor.engine_aaa.persistence

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TickerRepository : ReactiveCassandraRepository<Ticker, UUID>
