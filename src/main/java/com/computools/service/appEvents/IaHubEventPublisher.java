package com.computools.service.appEvents;

import com.computools.service.iaHub.dto.LogsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IaHubEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishLogsEvent(final LogsDto logs) {
        log.debug("Publishing logs event = {}", logs);
        applicationEventPublisher.publishEvent(logs);
    }
}
