package com.computools.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.computools.repository.table.Logs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class LogsRepositoryImpl implements LogsRepository {

    private static final Integer DEFAULT_PAGE_SIZE = 20;

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Override
    public Logs save(Logs table) {
        dynamoDBMapper.save(table);
        return table;
    }

    @Override
    public List<Logs> findAll() {
        PaginatedScanList<Logs> scan =
                dynamoDBMapper.scan(Logs.class, new DynamoDBScanExpression());
        return scan;
    }

    @Override
    public Optional<Logs> findByHashKey(String logId) {
        Logs log =
                dynamoDBMapper.load(Logs.class, logId);
        return Optional.ofNullable(log);
    }

    @Override
    public QueryResultPage<Logs> findByHashIndex(Long tenantNo, Map<String, AttributeValue> lastEvaluatedKey) {
        DynamoDBQueryExpression dynamoDBQueryExpression = new DynamoDBQueryExpression();
        dynamoDBQueryExpression
                .withHashKeyValues(new Logs(tenantNo))
                .withConsistentRead(false)
                .withLimit(DEFAULT_PAGE_SIZE)
                .withScanIndexForward(false)
                .setExclusiveStartKey(lastEvaluatedKey);
        QueryResultPage<Logs> result =
                dynamoDBMapper.queryPage(Logs.class, dynamoDBQueryExpression);
        return result;
    }

    @Override
    public QueryResultPage<Logs> findByCompositeIndexBetween(Long tenantNo, Long eventTimestampFrom, Long eventTimestampTo, Map<String, AttributeValue> lastEvaluatedKey) {
        DynamoDBQueryExpression dynamoDBQueryExpression = new DynamoDBQueryExpression();
        dynamoDBQueryExpression
                .withHashKeyValues(new Logs(tenantNo))
                .withConsistentRead(false)
                .withLimit(DEFAULT_PAGE_SIZE)
                .withScanIndexForward(false)
                .setExclusiveStartKey(lastEvaluatedKey);

        if (eventTimestampFrom!=null && eventTimestampTo!=null) {
            dynamoDBQueryExpression.withRangeKeyCondition("eventTimestamp",
                    new Condition()
                            .withComparisonOperator(ComparisonOperator.BETWEEN)
                            .withAttributeValueList(
                                    new AttributeValue().withN(String.valueOf(eventTimestampFrom)),
                                    new AttributeValue().withN(String.valueOf(eventTimestampTo))));
        } else if (eventTimestampFrom!=null && eventTimestampTo==null) {
            dynamoDBQueryExpression.withRangeKeyCondition("eventTimestamp",
                    new Condition()
                            .withComparisonOperator(ComparisonOperator.GT)
                            .withAttributeValueList(
                                    new AttributeValue().withN(String.valueOf(eventTimestampFrom))));
        } else if (eventTimestampFrom==null && eventTimestampTo!=null) {
            dynamoDBQueryExpression.withRangeKeyCondition("eventTimestamp",
                    new Condition()
                            .withComparisonOperator(ComparisonOperator.LE)
                            .withAttributeValueList(
                                    new AttributeValue().withN(String.valueOf(eventTimestampTo))));
        } else {
            throw new IllegalArgumentException("Timestamps params are nullable!");
        }

        QueryResultPage<Logs> result =
                dynamoDBMapper.queryPage(Logs.class, dynamoDBQueryExpression);
        return result;
    }
}
