package com.computools.web.iahub;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.computools.service.LogsService;
import com.computools.service.dto.LogsDto;
import com.computools.service.dto.QueryResultPageDto;
import com.computools.service.event.IaHubEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/logs")
public class LogsController {
    @Autowired
    private LogsService logsService;

    @Autowired
    private IaHubEventPublisher iaHubEventPublisher;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<LogsDto> findAll() {
        List<LogsDto> all = logsService.findAll();
        log.debug("Find all = {}", all);
        return all;
    }

    @GetMapping(value = "/{logId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public LogsDto findByHashKey(@PathVariable String logId) {
        log.debug("Find by hash key = {}", logId);
        Optional<LogsDto> byHashKey =
                logsService.findByHashKey(logId);
        return byHashKey.orElseThrow(() -> new RuntimeException("Item not found!"));
    }

    @PostMapping(value = "/{tenantNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public QueryResultPageDto<LogsDto> findByHashIndex(@PathVariable Long tenantNo,
                                                       @RequestBody(required = false) Map<String, AttributeValue> lastEvaluatedKey) {
        log.debug("Find by hash index = {}, lastEvaluatedKey = {}", tenantNo, lastEvaluatedKey);
        return logsService.findByHashIndex(tenantNo, lastEvaluatedKey);
    }

    @PostMapping(value = "/{tenantNo}/between", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public QueryResultPageDto<LogsDto> findByCompositeIndexBetween(@PathVariable Long tenantNo,
                                                                   @RequestParam(required = false) Long timestampFrom,
                                                                   @RequestParam(required = false) Long timestampTo,
                                                                   @RequestBody(required = false) Map<String, AttributeValue> lastEvaluatedKey) {
        log.debug("Find by composite index between = {}, {}, {}, {}", tenantNo, timestampFrom, timestampTo, lastEvaluatedKey);
        return logsService.findByCompositeIndexBetween(tenantNo, timestampFrom, timestampTo, lastEvaluatedKey);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public LogsDto save(@RequestBody @Valid LogsDto dto) {
        LogsDto savedWebhook = logsService.save(dto);
        log.debug("Saved config = {}", savedWebhook);
        iaHubEventPublisher.publishLogsEvent(dto);
        return savedWebhook;
    }

}
