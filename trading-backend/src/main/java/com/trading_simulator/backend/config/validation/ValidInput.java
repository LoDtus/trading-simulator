package com.trading_simulator.backend.config.validation;

import com.trading_simulator.backend.common.enums.InputType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = InputValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidInput {
    String message() default "Invalid input";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    InputType type();
}
