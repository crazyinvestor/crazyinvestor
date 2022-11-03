package net.crazyinvestor.ticker_producer_engine.event

import net.crazyinvestor.ticker_producer_engine.dto.response.BithumbWSOrderbookDepthResponse
import org.springframework.context.ApplicationEvent

class BithumbOrderbookDepthEvent(response: BithumbWSOrderbookDepthResponse) : ApplicationEvent(response)