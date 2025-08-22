package com.trading_simulator.backend.object.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SupportQAFilter {
    private String id;
    private String topic; // id

    private String keyword;
}
