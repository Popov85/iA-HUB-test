package com.computools.service.mapper;

import com.computools.repository.table.Configurations;
import com.computools.service.dto.ConfigurationsDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConfigurationsMapper {

    ConfigurationsDto toDto(Configurations table);
    Configurations toTable(ConfigurationsDto dto);
}
