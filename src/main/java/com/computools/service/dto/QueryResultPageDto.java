package com.computools.service.dto;

import lombok.Data;

import java.util.Map;

@Data
public class QueryResultPageDto {
    private Map<String, Object> lastEvaluatedKey;
    private Integer count;
    private Integer scannedCount;
    private Object consumedCapacity;
}
