package com.computools.service.mapper;

import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.computools.service.dto.QueryResultPageWebhooksDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {WebhooksMapper.class})
public interface QueryResultPageWebhooksMapper {

    QueryResultPageWebhooksDto toDto(QueryResultPage table);
}
