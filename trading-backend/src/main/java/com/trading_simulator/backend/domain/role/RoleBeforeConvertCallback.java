package com.trading_simulator.backend.domain.role;

import com.mongodb.lang.NonNull;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RoleBeforeConvertCallback implements BeforeConvertCallback<Role> {
    @Override
    public @NonNull Role onBeforeConvert(Role role, @NonNull String collection) {
        Instant now = Instant.now();
        return role.toBuilder()
                .id(role.getId() != null ? role.getId().trim() : null)
                .role(role.getRole() != null ? role.getRole().trim() : null)
                .description(role.getDescription() != null ? role.getDescription().trim() : null)
                .createdAt(role.getCreatedAt() == null ? now : role.getCreatedAt())
                .updatedAt(now)
                .build();
    }
}
