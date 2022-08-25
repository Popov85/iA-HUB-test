package com.computools.service.dto;

import com.computools.service.AccountType;
import com.computools.service.CredentialsType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfigurationsDto {

    @JsonProperty("tenantNo")
    private Long tenantNo;

    @JsonProperty("configurationId")
    private String configurationId;

    @NotNull
    @JsonProperty("configurationName")
    private String configurationName;

    @NotNull
    @JsonProperty("configurationCredentials")
    private Map<String, String> configurationCredentials;

    @NotNull
    @JsonProperty("accountType")
    private AccountType accountType;

    @NotNull
    @JsonProperty("credentialsType")
    private CredentialsType credentialsType;
}
