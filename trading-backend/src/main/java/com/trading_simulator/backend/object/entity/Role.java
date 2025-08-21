package com.trading_simulator.backend.object.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    private String id;
    private String role;
    private String description;
}
