package com.computools.service.iaHub;

import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.computools.repository.ConfigurationsRepository;
import com.computools.repository.table.Configurations;
import com.computools.service.iaHub.dto.*;
import com.computools.service.iaHub.mapper.ConfigurationsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConfigurationsServiceImpl implements ConfigurationsService {

    @Autowired
    private WebhooksService webhooksService;

    @Autowired
    private ConfigurationsRepository configurationsRepository;

    @Autowired
    private ConfigurationsMapper configurationsMapper;

    @Override
    public ConfigurationsDto save(ConfigurationsDto dto) {
        Configurations savedItem =
                configurationsRepository.save(configurationsMapper.toTable(dto));
        return configurationsMapper.toDto(savedItem);
    }

    @Override
    public List<ConfigurationsDto> findAll() {
        List<ConfigurationsDto> result = configurationsRepository.findAll()
                .stream().map(c -> configurationsMapper.toDto(c)).collect(Collectors.toList());
        return result;
    }

    @Override
    public Optional<ConfigurationsDto> findByCompositeKey(Long tenantNo, String configurationId) {
        Optional<ConfigurationsDto> configurationsDto =
                configurationsRepository.findByCompositeKey(tenantNo, configurationId).
                        map(c -> configurationsMapper.toDto(c));
        return configurationsDto;
    }

    @Override
    public QueryResultPageDto<ConfigurationsWebhooksDto> findByHashKey(Long tenantNo,
                                                                       Map<String, AttributeValue> lastEvaluatedKey) {
        QueryResultPage<Configurations> page =
                configurationsRepository.findByHashKey(tenantNo, lastEvaluatedKey);
        // Add webhooks for each configuration!
        List<ConfigurationsWebhooksDto> results = page.getResults()
                .stream()
                .map(c -> new ConfigurationsWebhooksDto(
                        configurationsMapper.toDto(c),
                        webhooksService.findByCompositeIndex(tenantNo, c.getConfigurationId())))
                .collect(Collectors.toList());

        return QueryResultPageDto.<ConfigurationsWebhooksDto>builder()
                        .results(results)
                        .lastEvaluatedKey(page.getLastEvaluatedKey())
                        .consumedCapacity(page.getConsumedCapacity())
                        .count(page.getCount())
                        .scannedCount(page.getScannedCount())
                        .build();
    }

    @Override
    public void deleteByCompositeKey(Long tenantNo, String configurationId) {
        if (webhooksService.existsByCompositeIndex(tenantNo, configurationId))
            throw new IllegalStateException("Configurations with associated webhooks cannot be deleted! Delete webhooks first!");
        configurationsRepository.deleteByCompositeKey(tenantNo, configurationId);
    }
}
