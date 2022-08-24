package com.computools.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class QueryResultPageWebhooksDto extends QueryResultPageDto {
    private List<WebhooksDto> results;
}
