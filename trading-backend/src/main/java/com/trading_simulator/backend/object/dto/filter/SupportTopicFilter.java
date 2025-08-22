package com.trading_simulator.backend.object.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SupportTopicFilter {
    private String id;
    private String owner;

    private String keyword;
}
