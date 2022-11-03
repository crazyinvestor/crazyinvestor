package net.crazyinvestor.ticker_producer_engine.event

import net.crazyinvestor.ticker_producer_engine.enums.ExchangeName
import org.springframework.context.ApplicationEvent

class DisconnectEvent(exchangeName: ExchangeName) : ApplicationEvent(exchangeName)