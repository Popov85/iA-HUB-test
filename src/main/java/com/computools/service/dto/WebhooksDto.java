package com.computools.service.dto;

import com.computools.service.AccountType;
import com.computools.service.StorageType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class WebhooksDto {
    @JsonProperty("webhookId")
    private String webhookId;
    @JsonProperty("storageType")
    private StorageType storageType;
    @JsonProperty("archiveFolder")
    private String archiveFolder;
    @JsonProperty("propertiesMapping")
    private Map<String, String> propertiesMapping;
    @JsonProperty("credentials")
    private Map<String, String> credentials;
    @JsonProperty("signingTool")
    private AccountType signingTool;
    @JsonProperty("tenantNo")
    private Long tenantNo;
    @JsonProperty("configurationId")
    private String configurationId;
}
