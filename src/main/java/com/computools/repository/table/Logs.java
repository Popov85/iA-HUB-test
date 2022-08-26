package com.computools.repository.table;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@DynamoDBTable(tableName = "Logs")
public class Logs {

    private String logId;
    private Long tenantNo;
    private Long eventTimestamp;
    private String logType;
    private String eventType;
    private String eventOutcome;
    private String eventFailureMessage;
    private String eventDocument;
    private String archivePath;
    private String accountType;
    private String credentialsType;
    private String configurationId;

    public Logs(Long tenantNo) {
        this.tenantNo = tenantNo;
    }

    @DynamoDBAutoGeneratedKey
    @DynamoDBHashKey(attributeName = "logId")
    public String getLogId() {
        return logId;
    }

    @DynamoDBIndexHashKey(attributeName = "tenantNo", globalSecondaryIndexName = "index-tenantNo-eventTimestamp")
    public Long getTenantNo() {
        return tenantNo;
    }

    @DynamoDBAutoGeneratedTimestamp(strategy=DynamoDBAutoGenerateStrategy.ALWAYS)
    @DynamoDBIndexRangeKey(attributeName = "eventTimestamp", globalSecondaryIndexName = "index-tenantNo-eventTimestamp")
    public Long getEventTimestamp() {
        return eventTimestamp;
    }

    @DynamoDBAttribute(attributeName = "logType")
    public String getLogType() {
        return logType;
    }

    @DynamoDBAttribute(attributeName = "eventType")
    public String getEventType() {
        return eventType;
    }

    @DynamoDBAttribute(attributeName = "eventOutcome")
    public String getEventOutcome() {
        return eventOutcome;
    }

    @DynamoDBAttribute(attributeName = "eventFailureMessage")
    public String getEventFailureMessage() {
        return eventFailureMessage;
    }

    @DynamoDBAttribute(attributeName = "eventDocument")
    public String getEventDocument() {
        return eventDocument;
    }

    @DynamoDBAttribute(attributeName = "archivePath")
    public String getArchivePath() {
        return archivePath;
    }

    @DynamoDBAttribute(attributeName = "accountType")
    public String getAccountType() {
        return accountType;
    }

    @DynamoDBAttribute(attributeName = "credentialsType")
    public String getCredentialsType() {
        return credentialsType;
    }

    @DynamoDBAttribute(attributeName = "configurationId")
    public String getConfigurationId() {
        return configurationId;
    }
}
