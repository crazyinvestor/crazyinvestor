package net.crazyinvestor.engine_aaa.config

import com.datastax.oss.driver.api.core.CqlSession
import net.crazyinvestor.engine_aaa.constants.DEFAULT_KEYSPACE
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.core.*
import org.springframework.data.cassandra.core.cql.session.DefaultBridgedReactiveSession

@Configuration
class CassandraConfiguration {
    @Bean
    fun reactiveCassandraTemplate(): ReactiveCassandraTemplate {
        val cqlSession: CqlSession = CqlSession.builder()
            .withKeyspace(DEFAULT_KEYSPACE)
            .build()

        return ReactiveCassandraTemplate(DefaultBridgedReactiveSession(cqlSession))
    }
}