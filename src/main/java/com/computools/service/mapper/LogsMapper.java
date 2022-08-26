package com.computools.service.mapper;

import com.computools.repository.table.Logs;
import com.computools.service.dto.LogsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LogsMapper {

    @Mapping(target = "tenantNo", ignore = true)
    LogsDto toDto(Logs table);
    Logs toTable(LogsDto dto);

    @Mapping(target = "tenantNo", ignore = true)
    List<LogsDto> toDto(List<Logs> table);

}
