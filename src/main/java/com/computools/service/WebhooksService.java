package com.computools.service;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.computools.service.dto.QueryResultPageWebhooksDto;
import com.computools.service.dto.WebhooksDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WebhooksService {
    WebhooksDto save(WebhooksDto dto);
    List<WebhooksDto> findAll();
    Optional<WebhooksDto> findByHashKey(String webhookId);
    void deleteByHashKey(String webhookId);
    QueryResultPageWebhooksDto findByCompositeIndex(Long tenantNo, String configurationId, Map<String, AttributeValue> lastEvaluatedKey);
    Boolean existsByCompositeIndex(Long tenantNo, String configurationId);
}
