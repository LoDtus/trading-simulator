package com.trading_simulator.backend.object.entity;

import com.trading_simulator.backend.common.enums.SupportTopicType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "support_topic")
public class SupportTopic {
    @Id
    private String id;
    private String owner;
    private String title;
    private SupportTopicType type;

    @Min(0) @Max(50)
    private Integer pin;

    private Instant createdAt;
    private Instant updatedAt;
}
