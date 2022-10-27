package net.crazyinvestor.engine_b.event;

import net.crazyinvestor.engine_b.dto.response.BithumbWSOrderbookDepthResponse;
import org.springframework.context.ApplicationEvent;

public final class BithumbOrderbookDepthEvent extends ApplicationEvent {
    public BithumbOrderbookDepthEvent(BithumbWSOrderbookDepthResponse response) {
        super(response);
    }
}
