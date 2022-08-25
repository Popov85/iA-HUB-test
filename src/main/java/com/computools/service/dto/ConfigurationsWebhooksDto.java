package com.computools.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfigurationsWebhooksDto{

    @JsonProperty("configuration")
    private ConfigurationsDto configuration;

    @JsonProperty("configurationWebhooks")
    private List<WebhooksDto> configurationWebhooks;

    public ConfigurationsWebhooksDto(ConfigurationsDto configuration,
                                     List<WebhooksDto> configurationWebhooks) {
        this.configuration = configuration;
        this.configurationWebhooks = configurationWebhooks;
    }
}
