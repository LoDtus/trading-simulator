package com.trading_simulator.backend.object;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ScenarioPosition {
    @Id
    private String id;
    private String scenarioId;

    private String assetSymbol; // BTC, ETH, AAPL...
    private BigDecimal quantity;
    private BigDecimal avgEntryPrice;

    private Instant createdAt;
    private Instant updatedAt;
}
