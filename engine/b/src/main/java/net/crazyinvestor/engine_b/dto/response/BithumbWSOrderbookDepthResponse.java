package net.crazyinvestor.engine_b.dto.response;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class BithumbWSOrderbookDepthResponse {
    private List<BithumbWSOrderbookDepthResponseContent> list;
    @Setter private Long datetime;
}
