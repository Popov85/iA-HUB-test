package com.computools.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.computools.repository.table.Configurations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class ConfigurationsRepositoryImpl implements ConfigurationsRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Override
    public Configurations save(Configurations configs) {
        dynamoDBMapper.save(configs);
        return configs;
    }

    @Override
    public PaginatedList<Configurations> findByHashKey(Long tenantNo) {
        DynamoDBQueryExpression<Configurations> queryExpression = new DynamoDBQueryExpression()
                .withHashKeyValues(new Configurations(tenantNo))
                .withConsistentRead(false);
        PaginatedList<Configurations> result =
                dynamoDBMapper.query(Configurations.class, queryExpression);
        return result;
    }

    @Override
    public Optional<Configurations> findByCompositeKey(Long tenantNo, String configurationId) {
        Configurations configs =
                dynamoDBMapper.load(Configurations.class, tenantNo, configurationId);
        return Optional.ofNullable(configs);
    }

    @Override
    public List<Configurations> findAll() {
        PaginatedScanList<Configurations> scan =
                dynamoDBMapper.scan(Configurations.class, new DynamoDBScanExpression());
        return scan;
    }

    @Override
    public void deleteByCompositeKey(Long tenantNo, String configurationId) {
        dynamoDBMapper.delete(new Configurations(tenantNo, configurationId));
        return;
    }
}
