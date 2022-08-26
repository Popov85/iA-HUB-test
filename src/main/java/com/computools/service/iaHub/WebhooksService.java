package com.computools.service.iaHub;

import com.computools.service.iaHub.dto.WebhooksDto;

import java.util.List;
import java.util.Optional;

public interface WebhooksService {
    WebhooksDto save(WebhooksDto dto);
    List<WebhooksDto> findAll();
    Optional<WebhooksDto> findByHashKey(String webhookId);
    void deleteByHashKey(String webhookId);
    List<WebhooksDto> findByCompositeIndex(Long tenantNo, String configurationId);
    Boolean existsByCompositeIndex(Long tenantNo, String configurationId);
}
