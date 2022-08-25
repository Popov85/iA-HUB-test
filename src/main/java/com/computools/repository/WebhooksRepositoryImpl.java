package com.computools.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.computools.repository.table.Webhooks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class WebhooksRepositoryImpl implements WebhooksRepository {

    private static final Integer DEFAULT_PAGE_SIZE = 10;

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Override
    public Webhooks save(Webhooks table) {
        dynamoDBMapper.save(table);
        return table;
    }

    @Override
    public List<Webhooks> findAll() {
        PaginatedScanList<Webhooks> scan =
                dynamoDBMapper.scan(Webhooks.class, new DynamoDBScanExpression());
        return scan;
    }

    @Override
    public Optional<Webhooks> findByHashKey(String webhookId) {
        Webhooks webhook =
                dynamoDBMapper.load(Webhooks.class, webhookId);
        return Optional.ofNullable(webhook);
    }

    @Override
    public Boolean existsByCompositeIndex(Long tenantNo, String configurationId) {
        final Webhooks webhooks = new Webhooks(tenantNo);
        final DynamoDBQueryExpression<Webhooks> queryExpression = new DynamoDBQueryExpression<>();
        queryExpression.withIndexName("index-tenantNo-configurationId")
                .withConsistentRead(false)
                .withProjectionExpression("webhookId")
                .withHashKeyValues(webhooks)
                .withLimit(1);
        queryExpression.withRangeKeyCondition("configurationId",
                new Condition()
                        .withComparisonOperator(ComparisonOperator.EQ)
                        .withAttributeValueList(new AttributeValue().withS(configurationId)));
        QueryResultPage<Webhooks> result = dynamoDBMapper.queryPage(Webhooks.class, queryExpression);
        return !(result==null || result.getResults().isEmpty());
    }

    @Override
    public PaginatedList<Webhooks> findByCompositeIndex(Long tenantNo, String configurationId) {
        final Webhooks webhooks = new Webhooks(tenantNo);

        final DynamoDBQueryExpression<Webhooks> queryExpression = new DynamoDBQueryExpression<>();

        queryExpression.withIndexName("index-tenantNo-configurationId")
                .withConsistentRead(false)
                .withProjectionExpression("webhookId,storageType,archiveFolder,propertiesMapping,credentials")
                .withHashKeyValues(webhooks);

        queryExpression.withRangeKeyCondition("configurationId",
                new Condition()
                        .withComparisonOperator(ComparisonOperator.EQ)
                        .withAttributeValueList(new AttributeValue().withS(configurationId)));

        PaginatedQueryList<Webhooks> result = dynamoDBMapper.query(Webhooks.class, queryExpression);

        return result;
    }

    @Override
    public void deleteByHashKey(String webhookId) {
        dynamoDBMapper.delete(new Webhooks(webhookId));
        return;
    }
}
