package com.trading_simulator.backend.object.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "trade_history")
public class TradeHistory { // Ghi lại toàn bộ giao dịch hoàn thành để phân tích và báo cáo.
    @Id
    private String id;
    private String scenarioId;

    private String assetSymbol;
    private BigDecimal quantity;
    private BigDecimal entryPrice;
    private BigDecimal exitPrice;
    private BigDecimal profitLoss;

    private Instant openedAt;
    private Instant closedAt;
}
