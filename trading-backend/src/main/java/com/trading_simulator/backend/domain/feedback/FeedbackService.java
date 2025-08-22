package com.trading_simulator.backend.domain.feedback;

public interface FeedbackService {
    Feedback save(Feedback feedback);
    Boolean deleteById(String id);
}
