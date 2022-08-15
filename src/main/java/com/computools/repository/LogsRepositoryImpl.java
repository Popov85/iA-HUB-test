package com.computools.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.computools.repository.table.Logs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class LogsRepositoryImpl implements LogsRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Override
    public Logs save(Logs log) {
        dynamoDBMapper.save(log);
        return log;
    }
    @Override
    public Optional<Logs> findById(String id) {
        Logs log =
                dynamoDBMapper.load(Logs.class, id);
        return Optional.ofNullable(log);
    }
    @Override
    public List<Logs> findAll() {
        PaginatedScanList<Logs> scan =
                dynamoDBMapper.scan(Logs.class, new DynamoDBScanExpression());
        return scan;
    }

    @Override
    public void deleteById(String id) {
        dynamoDBMapper.delete(new Logs(id));
        return;
    }

    @Override
    public Logs updateById(String id, Logs logs) {
        log.debug("Updating = {}", logs);
        dynamoDBMapper.save(logs,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("agreement_id",
                                new ExpectedAttributeValue(new AttributeValue().withS(id))));
        return logs;
    }
}
