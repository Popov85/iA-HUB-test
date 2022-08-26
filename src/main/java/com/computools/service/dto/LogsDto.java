package com.computools.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogsDto {

    @JsonProperty("logId")
    private String logId;
    @JsonProperty("tenantNo")
    private Long tenantNo;
    @JsonProperty("eventTimestamp")
    private Long eventTimestamp;
    @JsonProperty("logType")
    private String logType;
    @JsonProperty("eventType")
    private String eventType;
    @JsonProperty("eventOutcome")
    private String eventOutcome;
    @JsonProperty("eventFailureMessage")
    private String eventFailureMessage;
    @JsonProperty("eventDocument")
    private String eventDocument;
    @JsonProperty("archivePath")
    private String archivePath;
    @JsonProperty("accountType")
    private String accountType;
    @JsonProperty("credentialsType")
    private String credentialsType;
    @JsonProperty("configurationId")
    private String configurationId;

}
