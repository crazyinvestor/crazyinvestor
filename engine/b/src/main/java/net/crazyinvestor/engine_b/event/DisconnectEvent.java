package net.crazyinvestor.engine_b.event;

import net.crazyinvestor.engine_b.enums.ExchangeName;
import org.springframework.context.ApplicationEvent;

public final class DisconnectEvent extends ApplicationEvent {
    public DisconnectEvent(final ExchangeName exchangeName) {
        super(exchangeName);
    }
}
