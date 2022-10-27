package net.crazyinvestor.engine_b.event;

import net.crazyinvestor.engine_b.dto.response.BithumbWSTickerResponse;
import org.springframework.context.ApplicationEvent;

public final class BithumbTickerEvent extends ApplicationEvent {
    public BithumbTickerEvent(BithumbWSTickerResponse response) {
        super(response);
    }
}
