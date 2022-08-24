package com.computools.service.mapper;

import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.computools.service.dto.QueryResultPageConfigurationsDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ConfigurationsMapper.class})
public interface QueryResultPageConfigurationsMapper {

    QueryResultPageConfigurationsDto toDto(QueryResultPage table);
}
