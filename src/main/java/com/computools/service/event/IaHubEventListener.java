package com.computools.service.event;


import com.computools.service.dto.LogsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IaHubEventListener {

    @EventListener
    public void onApplicationEvent(LogsDto event) {
        log.debug("Received logs event = {}", event);
    }
}
