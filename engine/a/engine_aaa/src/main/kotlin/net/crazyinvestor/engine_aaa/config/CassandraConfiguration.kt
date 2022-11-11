package net.crazyinvestor.engine_aaa.config

import com.datastax.oss.driver.api.core.CqlSession
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.core.CassandraOperations
import org.springframework.data.cassandra.core.CassandraTemplate
import java.net.InetSocketAddress

@Configuration
class CassandraConfiguration {
    @Bean
    fun cassandraTemplate(): CassandraOperations {
        val cqlSession: CqlSession = CqlSession.builder()
            .withKeyspace(DEFAULT_KEYSPACE)
            .addContactPoint(InetSocketAddress("localhost", 9042))
            .withLocalDatacenter("datacenter1")
            .build()

        return CassandraTemplate(cqlSession)
    }
}
