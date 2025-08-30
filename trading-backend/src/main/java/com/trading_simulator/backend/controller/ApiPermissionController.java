package com.trading_simulator.backend.controller;

import com.trading_simulator.backend.object.entity.ApiPermissionRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/api-permission")
@RequiredArgsConstructor
@Tag(name = "Api Permission", description = "Các API theo dõi endpoint của hệ thống")
public class ApiPermissionController {
    private final ApiPermissionRepository apiPermissionRepository;

    @GetMapping("/get-all")
    public ResponseEntity<?> getApiPermission() {
        return ResponseEntity.ok(apiPermissionRepository.findAll());
    }
}
