package com.trading_simulator.backend.object.entity;

import com.trading_simulator.backend.common.enums.OrderStatus;
import com.trading_simulator.backend.common.enums.OrderType;
import com.trading_simulator.backend.common.enums.Side;
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
@Document(collection = "order")
public class Order { // lưu lại các lệnh giao dịch
    @Id
    private String id;
    private String scenarioId;

    private OrderType orderType;
    private Side side;
    private String assetSymbol;
    private BigDecimal quantity;
    private BigDecimal price;
    private OrderStatus status;

    private Instant executedAt;
    private Instant createdAt;
}
