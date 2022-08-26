package com.computools.service.iaHub.dto;

import com.computools.service.AccountType;
import com.computools.service.StorageType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebhooksDto {
    @JsonProperty("webhookId")
    private String webhookId;
    @NotNull
    @JsonProperty("storageType")
    private StorageType storageType;
    @NotNull
    @JsonProperty("archiveFolder")
    private String archiveFolder;
    @NotNull
    @JsonProperty("propertiesMapping")
    private Map<String, String> propertiesMapping;
    @NotNull
    @JsonProperty("credentials")
    private Map<String, String> credentials;
    @NotNull
    @JsonProperty("signingTool")
    private AccountType signingTool;
    @JsonProperty("tenantNo")
    private Long tenantNo;
    @NotNull
    @JsonProperty("configurationId")
    private String configurationId;
}
