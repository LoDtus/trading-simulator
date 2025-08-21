package com.trading_simulator.backend.object.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class QueryRequest<T> {
    private PaginationRequest pagination;
    private T filter;
}