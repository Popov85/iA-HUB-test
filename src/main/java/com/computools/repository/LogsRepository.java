package com.computools.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.computools.repository.table.Logs;

import java.util.Map;
import java.util.Optional;

public interface LogsRepository extends CommonRepository<Logs> {
    Optional<Logs> findByHashKey(String logId);
    // A page of most recent logs by tenant sorted by eventTimestamp
    QueryResultPage<Logs> findByHashIndex(Long tenantNo, Map<String, AttributeValue> lastEvaluatedKey);
    QueryResultPage<Logs> findByCompositeIndexBetween(Long tenantNo, Long eventTimestampFrom, Long eventTimestampTo, Map<String, AttributeValue> lastEvaluatedKey);
}
