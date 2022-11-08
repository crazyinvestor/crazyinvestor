package net.crazyinvestor.ticker_producer_engine.deprecated

import net.crazyinvestor.ticker_producer_engine.dto.bithumb.response.BithumbWSTickerResponse
import org.springframework.context.ApplicationEvent

class BithumbTickerEvent(response: BithumbWSTickerResponse) : ApplicationEvent(response)