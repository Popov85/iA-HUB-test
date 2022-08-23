package com.computools.service;

import com.computools.service.dto.ConfigurationsDto;

import java.util.List;
import java.util.Optional;

public interface ConfigurationsService {
    ConfigurationsDto save(ConfigurationsDto dto);
    List<ConfigurationsDto> findAll();
    Optional<ConfigurationsDto> findByCompositeKey(Long tenantNo, String configurationId);
    List<ConfigurationsDto> findByHashKey(Long tenantNo);
    void deleteByCompositeKey(Long tenantNo, String configurationId);
}
