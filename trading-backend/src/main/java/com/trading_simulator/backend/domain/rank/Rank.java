package com.trading_simulator.backend.domain.rank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "rank")
public class Rank {
    @Id
    private String id;
    private String userId;
    private String rank;
    // tổng tài sản, ROI...
}
