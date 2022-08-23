package com.computools.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;
import com.computools.repository.table.Configurations;

import java.util.Optional;

public interface ConfigurationsRepository extends CommonRepository<Configurations> {
    Optional<Configurations> findByCompositeKey(Long tenantNo, String configurationId);
    PaginatedList<Configurations> findByHashKey(Long tenantNo);
    void deleteByCompositeKey(Long tenantNo, String configurationId);
}
