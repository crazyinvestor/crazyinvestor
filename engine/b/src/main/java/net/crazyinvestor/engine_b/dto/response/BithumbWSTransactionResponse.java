package net.crazyinvestor.engine_b.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class BithumbWSTransactionResponse {
    private List<BithumbWSTransactionResponseContent> list;
}
