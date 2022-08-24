package com.computools.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.computools.repository.table.Webhooks;

import java.util.Map;
import java.util.Optional;

public interface WebhooksRepository extends CommonRepository<Webhooks> {
    Optional<Webhooks> findByHashKey(String webhookId);
    void deleteByHashKey(String webhookId);
    QueryResultPage<Webhooks> findByCompositeIndex(Long tenantNo, String configurationId, Map<String, AttributeValue> lastEvaluatedKey);
    Boolean existsByCompositeIndex(Long tenantNo, String configurationId);
}
