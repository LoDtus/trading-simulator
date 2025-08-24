package com.trading_simulator.backend.controller;

import com.trading_simulator.backend.domain.role.Role;
import com.trading_simulator.backend.domain.role.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
@Tag(name = "Role", description = "Các API tương tác với vai trò người dùng")
public class RoleController {
    private final RoleService roleService;

    @Operation(
            summary = "Lấy thông tin của vai trò",
            description = "Tìm kiếm, lọc ra các vai trò trong hệ thống"
    )
    @PostMapping("/get")
    public ResponseEntity<?> getRoles() {
        return ResponseEntity.ok("");
    }

    @Operation(
            summary = "Thêm vai trò",
            description = "Thêm mới một vai trò vào hệ thống"
    )
    @PostMapping("/add")
    public ResponseEntity<?> addRole(
            @RequestBody Role role
    ) {
//        if (roleService.findByRole(role.getRole().trim()) == null) {
//            return ResponseEntity.ok("");
//        }
//
//        // Lọc ký tự đặc biệt
//
//        role = role.toBuilder()
//                .id(null)
//                .role(role.getRole().trim())
//                .description(role.getDescription().trim())
//                .createdAt(Instant.now())
//                .updatedAt(Instant.now())
//                .build();
//        role = roleService.save(role);

        return ResponseEntity.ok("");
    }

    @Operation(
            summary = "Cập nhật vai trò",
            description = "Cập nhật thông tin của một vai trò bất kỳ trong hệ thống"
    )
    @PutMapping("/update")
    public ResponseEntity<?> updateRole(
            @RequestBody Role role
    ) {
        // Tìm kiếm và cập nhật
        return ResponseEntity.ok("");
    }

    @Operation(
            summary = "Xóa vai trò",
            description = "Xóa một vai trò bất kỳ trong hệ thống"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRole(
            @Parameter(description = "ID của vai trò", example = "ROLE_EXAMPLE", in = ParameterIn.PATH)
            @PathVariable String id
    ) {
        return ResponseEntity.ok("");
    }
}
