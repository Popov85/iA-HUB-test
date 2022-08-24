package com.computools.service.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class QueryResultPageConfigurationsDto extends QueryResultPageDto {
    private List<ConfigurationsDto> results;
}
