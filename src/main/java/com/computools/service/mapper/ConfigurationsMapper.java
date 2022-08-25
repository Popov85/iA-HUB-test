package com.computools.service.mapper;

import com.computools.repository.table.Configurations;
import com.computools.service.dto.ConfigurationsDto;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.util.List;

@Slf4j
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ConfigurationsMapper {

    private static final String CLIENT_SECRET_ATTRIBUTE_NAME = "clientSecret";
    private static final String INTEGRATION_KEY_ATTRIBUTE_NAME = "integrationKey";

    @Autowired
    private TextEncryptor textEncryptor;

    @Mapping(target = "tenantNo", ignore = true)
    public abstract ConfigurationsDto toDto(Configurations table);
    public abstract Configurations toTable(ConfigurationsDto dto);
    @Mapping(target = "tenantNo", ignore = true)
    public abstract List<ConfigurationsDto> toDto(List<Configurations> table);

    @AfterMapping
    void encryptPassword(ConfigurationsDto dto, @MappingTarget Configurations table) {
        table.getConfigurationCredentials()
                .computeIfPresent(CLIENT_SECRET_ATTRIBUTE_NAME, (k, v) -> textEncryptor.encrypt(v));
        table.getConfigurationCredentials()
                .computeIfPresent(INTEGRATION_KEY_ATTRIBUTE_NAME, (k, v) -> textEncryptor.encrypt(v));
    }

    @AfterMapping
    void decryptPassword(Configurations table, @MappingTarget ConfigurationsDto dto) {
        dto.getConfigurationCredentials()
                .computeIfPresent(CLIENT_SECRET_ATTRIBUTE_NAME, (k, v) -> textEncryptor.decrypt(v));
        dto.getConfigurationCredentials()
                .computeIfPresent(INTEGRATION_KEY_ATTRIBUTE_NAME, (k, v) -> textEncryptor.decrypt(v));
    }
}
