package com.trading_simulator.backend.externalservice;

public interface EmailService {
    Boolean sendEmailVerification();
    Boolean sendResetPasswordEmail();
    Boolean sendCustomEmail();
}
