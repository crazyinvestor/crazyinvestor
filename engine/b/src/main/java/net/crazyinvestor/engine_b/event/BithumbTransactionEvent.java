package net.crazyinvestor.engine_b.event;

import net.crazyinvestor.engine_b.dto.response.BithumbWSTransactionResponse;
import org.springframework.context.ApplicationEvent;

public final class BithumbTransactionEvent extends ApplicationEvent {
    public BithumbTransactionEvent(BithumbWSTransactionResponse response) {
        super(response);
    }
}
