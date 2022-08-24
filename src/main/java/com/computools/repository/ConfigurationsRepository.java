package com.computools.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.computools.repository.table.Configurations;

import java.util.Map;
import java.util.Optional;

public interface ConfigurationsRepository extends CommonRepository<Configurations> {
    Optional<Configurations> findByCompositeKey(Long tenantNo, String configurationId);
    QueryResultPage<Configurations> findByHashKey(Long tenantNo, Map<String, AttributeValue> lastEvaluatedKey);
    void deleteByCompositeKey(Long tenantNo, String configurationId);
}
