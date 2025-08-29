package com.trading_simulator.backend.object;

import jakarta.validation.constraints.PastOrPresent;
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

    @PastOrPresent(message = "The creation time cannot be in the future")
    private Instant createdAt;

    @PastOrPresent(message = "The update time cannot be in the future")
    private Instant updatedAt;
}
