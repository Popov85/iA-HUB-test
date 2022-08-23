package com.computools.web;

import com.computools.service.ConfigurationsService;
import com.computools.service.dto.ConfigurationsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    // TODO: next page?
    @GetMapping(value = "/{tenantNo}",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ConfigurationsDto> findByHashKey(@PathVariable Long tenantNo) {
        log.debug("Find by hash key = {}", tenantNo);
        List<ConfigurationsDto> byHashKey =
                configurationsService.findByHashKey(tenantNo);
        return byHashKey;
    }

    @GetMapping(value = "/{tenantNo}/{configurationId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ConfigurationsDto findByCompositeKey(@PathVariable Long tenantNo, @PathVariable String configurationId) {
        log.debug("Find by composite key = {}, {}", tenantNo, configurationId);
        Optional<ConfigurationsDto> byHashKey =
                configurationsService.findByCompositeKey(tenantNo, configurationId);
        return byHashKey.orElseThrow(()->new RuntimeException("Configuration is not found!"));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ConfigurationsDto save(@RequestBody ConfigurationsDto dto) {
        ConfigurationsDto savedConfig = configurationsService.save(dto);
        log.debug("Saved config = {}", savedConfig);
        return savedConfig;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ConfigurationsDto update(@RequestBody ConfigurationsDto newConfig) {
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
