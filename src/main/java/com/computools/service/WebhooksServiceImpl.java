package com.computools.service;

import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.computools.repository.WebhooksRepository;
import com.computools.repository.table.Webhooks;
import com.computools.service.dto.QueryResultPageWebhooksDto;
import com.computools.service.dto.WebhooksDto;
import com.computools.service.mapper.QueryResultPageWebhooksMapper;
import com.computools.service.mapper.WebhooksMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WebhooksServiceImpl implements WebhooksService {

    @Autowired
    private WebhooksRepository webhooksRepository;

    @Autowired
    private WebhooksMapper webhooksMapper;

    @Autowired
    private QueryResultPageWebhooksMapper queryResultPageWebhooksMapper;

    @Override
    public WebhooksDto save(WebhooksDto dto) {
        return webhooksMapper.toDto
                (webhooksRepository.save(webhooksMapper.toTable(dto)));
    }

    @Override
    public List<WebhooksDto> findAll() {
        return webhooksRepository
                .findAll().stream().map(w->webhooksMapper.toDto(w)).collect(Collectors.toList());
    }

    @Override
    public Optional<WebhooksDto> findByHashKey(String webhookId) {
        return webhooksRepository
                .findByHashKey(webhookId).map(w->webhooksMapper.toDto(w));
    }

    @Override
    public void deleteByHashKey(String webhookId) {
        webhooksRepository.deleteByHashKey(webhookId);
        return;
    }

    @Override
    public Boolean existsByCompositeIndex(Long tenantNo, String configurationId) {
        return webhooksRepository.existsByCompositeIndex(tenantNo, configurationId);
    }

    @Override
    public QueryResultPageWebhooksDto findByCompositeIndex(Long tenantNo, String configurationId,
                                                          Map<String, AttributeValue> lastEvaluatedKey) {
        QueryResultPage<Webhooks> byCompositeIndex =
                webhooksRepository.findByCompositeIndex(tenantNo, configurationId, lastEvaluatedKey);
        QueryResultPageWebhooksDto result = queryResultPageWebhooksMapper.toDto(byCompositeIndex);
        return result;
    }
}
