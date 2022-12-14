package com.computools.repository.table;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Setter
@NoArgsConstructor
@DynamoDBTable(tableName = "Webhooks")
public class Webhooks {

    private String webhookId;

    private String storageType;

    private String archiveFolder;

    private Map<String, String> propertiesMapping;

    private Map<String, String> credentials;

    private String signingTool;

    private Long tenantNo;

    private String configurationId;

    public Webhooks(String webhookId) {
        this.webhookId = webhookId;
    }

    public Webhooks(Long tenantNo) {
        this.tenantNo = tenantNo;
    }

    @DynamoDBAutoGeneratedKey
    @DynamoDBHashKey(attributeName = "webhookId")
    public String getWebhookId() {
        return webhookId;
    }

    @DynamoDBAttribute(attributeName = "storageType")
    public String getStorageType() {
        return storageType;
    }

    @DynamoDBAttribute(attributeName = "archiveFolder")
    public String getArchiveFolder() {
        return archiveFolder;
    }

    @DynamoDBAttribute(attributeName = "propertiesMapping")
    public Map<String, String> getPropertiesMapping() {
        return propertiesMapping;
    }

    @DynamoDBAttribute(attributeName = "credentials")
    public Map<String, String> getCredentials() {
        return credentials;
    }

    @DynamoDBAttribute(attributeName = "signingTool")
    public String getSigningTool() {
        return signingTool;
    }

    @DynamoDBIndexHashKey(attributeName = "tenantNo", globalSecondaryIndexName = "index-tenantNo-configurationId")
    public Long getTenantNo() {
        return tenantNo;
    }

    @DynamoDBIndexRangeKey(attributeName = "configurationId", globalSecondaryIndexName = "index-tenantNo-configurationId")
    public String getConfigurationId() {
        return configurationId;
    }
}
