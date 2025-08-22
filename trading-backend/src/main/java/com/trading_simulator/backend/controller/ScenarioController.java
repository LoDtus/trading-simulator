package com.trading_simulator.backend.controller;

import com.trading_simulator.backend.common.enums.ScenarioStatus;
import com.trading_simulator.backend.domain.auth.Auth;
import com.trading_simulator.backend.domain.scenario.Scenario;
import com.trading_simulator.backend.domain.auth.AuthService;
import com.trading_simulator.backend.domain.scenario.ScenarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/scenario")
@RequiredArgsConstructor
@Tag(name = "Scenario", description = "Các API tương tác với kịch bản giao dịch")
public class ScenarioController {
    private final AuthService authService;
    private final ScenarioService scenarioService;

    @PostMapping("/create")
    public ResponseEntity<?> createScenario(@RequestBody Scenario newScenario) {
        // chuyển sang việc xác định null tại entity thay vì check đi, check lại tại controller
        if (newScenario.getOwner() == null || newScenario.getOwner().isBlank()) {

        }

        Auth auth = authService.findById(newScenario.getOwner());
        if (auth == null) {

        }

        newScenario = newScenario.toBuilder()
                .id(null)
                .name(newScenario.getName().isBlank() ? "Untitled Scenario" : newScenario.getName())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .status(ScenarioStatus.ACTIVE)
                .build();
        newScenario = scenarioService.save(newScenario);
        return ResponseEntity.ok(newScenario);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateScenario(@RequestBody Scenario newScenario) {

        newScenario = newScenario.toBuilder()
                .updatedAt(Instant.now())
                .build();
        newScenario = scenarioService.save(newScenario);
        return ResponseEntity.ok(newScenario);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteScenarios(@RequestParam List<String> ids) {
        return ResponseEntity.ok("");
    }
}
