package com.trading_simulator.backend.object.entity;

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
@Document(collection = "local_file")
public class LocalFile {
    @Id
    private String id;
    private String eventId;
    private String owner;
    private String name;
    private String url;
    private String size;
    private Instant createdAt;
}
