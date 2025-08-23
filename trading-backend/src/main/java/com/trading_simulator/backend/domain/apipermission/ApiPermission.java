package com.trading_simulator.backend.domain.apipermission;

import com.trading_simulator.backend.common.enums.RoleConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "api_permission")
public class ApiPermission {
    @Id
    private String id;

    @Indexed(unique = true)
    private String pattern;
    private String method;
    private List<RoleConfig> roles;
    private String description;
    private Boolean enabled;
}
