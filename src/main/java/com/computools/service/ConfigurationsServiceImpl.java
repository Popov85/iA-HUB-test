package com.computools.service;

import com.computools.repository.ConfigurationsRepository;
import com.computools.repository.table.Configurations;
import com.computools.service.dto.ConfigurationsDto;
import com.computools.service.mapper.ConfigurationsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConfigurationsServiceImpl implements ConfigurationsService {

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
                configurationsRepository.findByCompositeKey(tenantNo, configurationId).map(c -> configurationsMapper.toDto(c));
        return configurationsDto;
    }

    @Override
    public List<ConfigurationsDto> findByHashKey(Long tenantNo) {
        List<ConfigurationsDto> result = configurationsRepository
                .findByHashKey(tenantNo).stream().map(c->configurationsMapper.toDto(c)).collect(Collectors.toList());
        return result;
    }

    @Override
    public void deleteByCompositeKey(Long tenantNo, String configurationId) {
        configurationsRepository.deleteByCompositeKey(tenantNo, configurationId);
    }
}
