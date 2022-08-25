package com.computools.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;
import com.computools.repository.table.Webhooks;

import java.util.Optional;

public interface WebhooksRepository extends CommonRepository<Webhooks> {
    Optional<Webhooks> findByHashKey(String webhookId);
    void deleteByHashKey(String webhookId);
    PaginatedList<Webhooks> findByCompositeIndex(Long tenantNo, String configurationId);
    Boolean existsByCompositeIndex(Long tenantNo, String configurationId);
}
