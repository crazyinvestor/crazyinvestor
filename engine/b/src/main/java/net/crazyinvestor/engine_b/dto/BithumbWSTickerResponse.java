package net.crazyinvestor.engine_b.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class BithumbWSTickerResponse {
    private String symbol;
    private String tickType;
    private String date;
    private String time;
    private String openPrice;
    private String closePrice;
    private String lowPrice;
    private String highPrice;
    private String value;
    private String volume;
    private String sellVolume;
    private String buyVolume;
    private String prevClosePrice;
    private String chgRate;
    private String chgAmt;
    private String volumePower;
}
