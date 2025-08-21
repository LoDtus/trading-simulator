package com.trading_simulator.backend.object.entity;

import com.trading_simulator.backend.common.enums.RoleDb;
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
@Document(collection = "auth")
public class Auth {
    @Id
    private String id;
    private String email;
    private String username;
    private String password;
    private RoleDb role;
    private Boolean active;
}
