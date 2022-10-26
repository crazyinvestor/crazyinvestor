package net.crazyinvestor.engine_b.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class BithumbWSTransactionResponseContent {
    private String symbol;
    private String buySellGb;
    private String contPrice;
    private String contQty;
    private String contAmt;
    private String contDtm;
    private String updn;
}
