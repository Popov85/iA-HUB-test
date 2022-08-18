package com.computools.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.computools.repository.table.Configs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class ConfigsRepositoryImpl implements ConfigsRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Override
    public Configs save(Configs configs) {
        dynamoDBMapper.save(configs);
        return configs;
    }

    @Override
    public Optional<Configs> findById(String id) {
        Configs configs =
                dynamoDBMapper.load(Configs.class, id);
        return Optional.ofNullable(configs);
    }

    @Override
    public List<Configs> findAll() {
        PaginatedScanList<Configs> scan =
                dynamoDBMapper.scan(Configs.class, new DynamoDBScanExpression());
        return scan;
    }

    @Override
    public void deleteById(String id) {
        dynamoDBMapper.delete(new Configs(id));
        return;
    }

    @Override
    public Configs updateById(String id, Configs configs) {
        log.debug("Updating = {}", configs);
        dynamoDBMapper.save(configs,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("config_id",
                                new ExpectedAttributeValue(new AttributeValue().withS(id))));
        return configs;
    }
}
