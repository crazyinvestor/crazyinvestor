package net.crazyinvestor.engine_aaa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EngineAaaApplication {
    companion object{
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<EngineAaaApplication>(*args)
        }
    }

}

