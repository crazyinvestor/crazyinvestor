package net.crazyinvestor.engine_aaa.persistence

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository
import org.springframework.stereotype.Repository
import java.util.UUID

// TODO deprecate 시키기
@Repository
interface TickerRepository : ReactiveCassandraRepository<Ticker, UUID>
