package net.crazyinvestor.engine_b.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BithumbTickTypes {
    MIN_30("30M"),
    HR_1("1H"),
    HR_12("12H"),
    HR_24("24H"),
    MID("MID"),
    ;

    private final String tickType;
}
