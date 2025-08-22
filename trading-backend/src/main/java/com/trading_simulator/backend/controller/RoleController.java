package com.trading_simulator.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
@Tag(name = "Role", description = "Các API tương tác với vai trò người dùng")
public class RoleController {
    @Operation(
            summary = "Xóa vai trò",
            description = "Xóa 1 vai trò bất kỳ trong hệ thống"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRole(
            @Parameter(description = "ID của vai trò", example = "ROLE_EXAMPLE", in = ParameterIn.PATH)
            @PathVariable String id
    ) {
        return ResponseEntity.ok("");
    }
}
