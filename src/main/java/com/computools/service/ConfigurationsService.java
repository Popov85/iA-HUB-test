package com.computools.service;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.computools.service.dto.ConfigurationsDto;
import com.computools.service.dto.QueryResultPageConfigurationsDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ConfigurationsService {
    ConfigurationsDto save(ConfigurationsDto dto);
    List<ConfigurationsDto> findAll();
    Optional<ConfigurationsDto> findByCompositeKey(Long tenantNo, String configurationId);
    QueryResultPageConfigurationsDto findByHashKey(Long tenantNo, Map<String, AttributeValue> lastEvaluatedKey);
    void deleteByCompositeKey(Long tenantNo, String configurationId);
}
