package com.trading_simulator.backend.controller;

import com.trading_simulator.backend.service.entityservice.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final ProfileService profileService;

    @PostMapping("/get")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok("");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser() {


        return ResponseEntity.ok("");
    }

    @PutMapping("/delete")
    public ResponseEntity<?> deleteUsers(@RequestParam List<String> ids) {
        for (String id : ids) {

        }

        return ResponseEntity.ok("");
    }
}
