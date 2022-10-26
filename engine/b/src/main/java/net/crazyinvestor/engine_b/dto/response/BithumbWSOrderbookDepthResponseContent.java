package net.crazyinvestor.engine_b.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class BithumbWSOrderbookDepthResponseContent {
    private String symbol;
    private String orderType;
    private String price;
    private String quantity;
    private String total;
}