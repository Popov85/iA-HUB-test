package com.computools.service.iaHub;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.computools.service.iaHub.dto.LogsDto;
import com.computools.service.iaHub.dto.QueryResultPageDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LogsService {
    LogsDto save(LogsDto dto);
    List<LogsDto> findAll();
    Optional<LogsDto> findByHashKey(String logId);
    // A page of most recent logs by tenant sorted by eventTimestamp
    QueryResultPageDto<LogsDto> findByHashIndex(Long tenantNo, Map<String, AttributeValue> lastEvaluatedKey);
    QueryResultPageDto<LogsDto> findByCompositeIndexBetween(Long tenantNo, Long eventTimestampFrom, Long eventTimestampTo, Map<String, AttributeValue> lastEvaluatedKey);
}
