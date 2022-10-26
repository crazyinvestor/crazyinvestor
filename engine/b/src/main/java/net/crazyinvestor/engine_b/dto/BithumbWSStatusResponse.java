package net.crazyinvestor.engine_b.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class BithumbWSStatusResponse {
    private String status;
    private String resmsg;
}
