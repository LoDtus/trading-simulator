package com.trading_simulator.backend.object.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank
    @Indexed(unique = true)
    private String pattern;

    private String method;
    private List<String> roleIds;
    private String description;

    @NotNull
    private Boolean enabled;
}
