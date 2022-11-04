package net.crazyinvestor.ticker_producer_engine

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.util.Collections

@SpringBootApplication
class TickerProducerEngineApplication {
	companion object {
		@JvmStatic
		fun main(args: Array<String>){
			val app = SpringApplication(TickerProducerEngineApplication::class.java)
			app.setDefaultProperties(Collections.singletonMap<String, Any>("server.port", "8089"))
			app.run(*args)
		}
	}
}
