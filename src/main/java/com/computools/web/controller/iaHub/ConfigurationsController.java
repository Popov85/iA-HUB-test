package com.computools.web.controller.iaHub;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.computools.service.iaHub.ConfigurationsService;
import com.computools.service.iaHub.dto.ConfigurationsDto;
import com.computools.service.iaHub.dto.ConfigurationsWebhooksDto;
import com.computools.service.iaHub.dto.QueryResultPageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/configurations")
public class ConfigurationsController {
    @Autowired
    private ConfigurationsService configurationsService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ConfigurationsDto> findAll() {
        List<ConfigurationsDto> all = configurationsService.findAll();
        log.debug("Find all = {}", all);
        return all;
    }

    @GetMapping(value = "/{tenantNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public QueryResultPageDto<ConfigurationsWebhooksDto> findByHashKey(@PathVariable Long tenantNo,
                                                                       @RequestParam(required = false) Map<String, AttributeValue> lastEvaluatedKey) {
        log.debug("Find by hash key = {}", tenantNo);
        QueryResultPageDto<ConfigurationsWebhooksDto> byHashKey =
                configurationsService.findByHashKey(tenantNo,
                        ((lastEvaluatedKey != null && !lastEvaluatedKey.isEmpty())) ? lastEvaluatedKey : null);
        return byHashKey;
    }

    @GetMapping(value = "/{tenantNo}/{configurationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ConfigurationsDto findByCompositeKey(@PathVariable Long tenantNo, @PathVariable String configurationId) {
        log.debug("Find by composite key = {}, {}", tenantNo, configurationId);
        Optional<ConfigurationsDto> byHashKey =
                configurationsService.findByCompositeKey(tenantNo, configurationId);
        return byHashKey.orElseThrow(() -> new RuntimeException("Configuration is not found!"));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ConfigurationsDto save(@RequestBody @Valid ConfigurationsDto dto) {
        ConfigurationsDto savedConfig = configurationsService.save(dto);
        log.debug("Saved config = {}", savedConfig);
        return savedConfig;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ConfigurationsDto update(@RequestBody @Valid ConfigurationsDto newConfig) {
        log.debug("Update = {}, {}", newConfig.getTenantNo(), newConfig.getConfigurationId());
        configurationsService.save(newConfig);
        return newConfig;
    }

    @DeleteMapping("/{tenantNo}/{configurationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByCompositeKey(@PathVariable Long tenantNo, @PathVariable String configurationId) {
        log.debug("Delete by composite key = {}, []", tenantNo, configurationId);
        configurationsService.deleteByCompositeKey(tenantNo, configurationId);
        return;
    }

}
