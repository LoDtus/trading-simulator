package com.trading_simulator.backend.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "user")
public class User {
    @Id
    private String id;
    private String image;
    private Instant status;
    private String bio;
    private Instant dateOfBirth;
    private List<String> address; // [nation, city]
    private Instant createdAt;
}
