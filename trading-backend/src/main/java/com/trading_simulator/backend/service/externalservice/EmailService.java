package com.trading_simulator.backend.service.externalservice;

public interface EmailService {
    Boolean sendEmailVerification();
    Boolean sendResetPasswordEmail();
    Boolean sendCustomEmail();
}
