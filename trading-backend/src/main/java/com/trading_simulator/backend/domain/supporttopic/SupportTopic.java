package com.trading_simulator.backend.domain.supporttopic;

import com.trading_simulator.backend.common.enums.SupportTopicType;
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
    private Integer pin;

    private Instant createdAt;
    private Instant updatedAt;
}
