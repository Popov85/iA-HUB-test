package com.computools.service;

import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.computools.repository.LogsRepository;
import com.computools.repository.table.Logs;
import com.computools.service.dto.LogsDto;
import com.computools.service.dto.QueryResultPageDto;
import com.computools.service.mapper.LogsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LogsServiceImpl implements LogsService {

    @Autowired
    private LogsRepository logsRepository;

    @Autowired
    private LogsMapper logsMapper;


    @Override
    public LogsDto save(LogsDto dto) {
        return logsMapper.toDto(logsRepository.save(logsMapper.toTable(dto)));
    }

    @Override
    public List<LogsDto> findAll() {
        return logsRepository.findAll()
                .stream().map(l->logsMapper.toDto(l)).collect(Collectors.toList());
    }

    @Override
    public Optional<LogsDto> findByHashKey(String logId) {
        return logsRepository.findByHashKey(logId).map(l->logsMapper.toDto(l));
    }

    @Override
    public QueryResultPageDto<LogsDto> findByHashIndex(Long tenantNo, Map<String, AttributeValue> lastEvaluatedKey) {
        QueryResultPage<Logs> page =
                logsRepository.findByHashIndex(tenantNo, lastEvaluatedKey);
        return QueryResultPageDto.<LogsDto>builder()
                .results(logsMapper.toDto(page.getResults()))
                .lastEvaluatedKey(page.getLastEvaluatedKey())
                .consumedCapacity(page.getConsumedCapacity())
                .count(page.getCount())
                .scannedCount(page.getScannedCount())
                .build();
    }

    @Override
    public QueryResultPageDto<LogsDto> findByCompositeIndexBetween(Long tenantNo, Long eventTimestampFrom, Long eventTimestampTo, Map<String, AttributeValue> lastEvaluatedKey) {
        QueryResultPage<Logs> page =
                logsRepository.findByCompositeIndexBetween(tenantNo, eventTimestampFrom, eventTimestampTo, lastEvaluatedKey);
        return QueryResultPageDto.<LogsDto>builder()
                .results(logsMapper.toDto(page.getResults()))
                .lastEvaluatedKey(page.getLastEvaluatedKey())
                .consumedCapacity(page.getConsumedCapacity())
                .count(page.getCount())
                .scannedCount(page.getScannedCount())
                .build();
    }
}
