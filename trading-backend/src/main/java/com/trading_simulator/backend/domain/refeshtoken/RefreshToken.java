package com.trading_simulator.backend.domain.refeshtoken;

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
@Document(collection = "refresh_token")
public class RefreshToken {
    @Id
    private String refreshToken;
    private String owner;
    private String exp;
}
