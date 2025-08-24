package com.trading_simulator.backend.config.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
public class NotFoundException extends RuntimeException {
    private final String errorCode = "NOT_FOUND";

    public NotFoundException(String message) { super(message); }
    public NotFoundException(String message, Throwable cause) { super(message, cause); }
}