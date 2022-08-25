package com.computools.service.dto;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConsumedCapacity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class QueryResultPageDto<T> {
    private List<T> results;
    private Map<String, AttributeValue> lastEvaluatedKey;
    private Integer count;
    private Integer scannedCount;
    private ConsumedCapacity consumedCapacity;
}
