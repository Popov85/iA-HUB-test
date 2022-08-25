package com.computools.service.mapper;

import com.computools.repository.table.Webhooks;
import com.computools.service.dto.WebhooksDto;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.util.List;

@Slf4j
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class WebhooksMapper {

    private static final String PASSWORD_ATTRIBUTE_NAME = "password";

    @Autowired
    private TextEncryptor textEncryptor;

    @Mapping(target = "tenantNo", ignore = true)
    @Mapping(target = "configurationId", ignore = true)
    public abstract WebhooksDto toDto(Webhooks table);
    public abstract Webhooks toTable(WebhooksDto dto);

    @Mapping(target = "tenantNo", ignore = true)
    @Mapping(target = "configurationId", ignore = true)
    public abstract List<WebhooksDto> toDto(List<Webhooks> table);

    @AfterMapping
    void encryptPassword(WebhooksDto dto, @MappingTarget Webhooks table) {
        //log.debug("Encrypt Creds = {}", dto.getCredentials());
        table.getCredentials()
                .computeIfPresent(PASSWORD_ATTRIBUTE_NAME, (k, v) -> textEncryptor.encrypt(v));
    }

    @AfterMapping
    void decryptPassword(Webhooks table, @MappingTarget WebhooksDto dto) {
        //log.debug("Decrypt Creds = {}", dto.getCredentials());
        dto.getCredentials()
                .computeIfPresent(PASSWORD_ATTRIBUTE_NAME, (k, v) -> textEncryptor.decrypt(v));
    }


}
