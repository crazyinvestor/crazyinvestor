package net.crazyinvestor.ticker_producer_engine.event

import net.crazyinvestor.ticker_producer_engine.dto.response.BithumbWSTickerResponse
import org.springframework.context.ApplicationEvent

class BithumbTickerEvent(response: BithumbWSTickerResponse) : ApplicationEvent(response)