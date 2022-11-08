package net.crazyinvestor.engine_aaa

import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.support.serializer.JsonDeserializer
import java.util.*

@SpringBootApplication
class EngineAaaApplication {
    @Bean
    fun stringDeserializer(): StringDeserializer {
        return StringDeserializer()
    }

    @Bean
    fun <T> jsonDeserializer(): JsonDeserializer<T> {
        return JsonDeserializer<T>().trustedPackages("*")
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val app = SpringApplication(EngineAaaApplication::class.java)
            app.setDefaultProperties(Collections.singletonMap<String, Any>("server.port", "8099"))
            app.run(*args)
        }
    }
}

