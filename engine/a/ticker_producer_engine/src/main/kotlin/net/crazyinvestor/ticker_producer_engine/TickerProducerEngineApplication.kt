package net.crazyinvestor.ticker_producer_engine

//import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
//import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
//import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.socket.config.annotation.EnableWebSocket

@SpringBootApplication
class TickerProducerEngineApplication {
	companion object {
		@JvmStatic
		fun main(args: Array<String>){
			runApplication<TickerProducerEngineApplication>(*args)
		}
	}
}
