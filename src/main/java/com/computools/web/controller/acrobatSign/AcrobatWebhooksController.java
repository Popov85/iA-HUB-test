package com.computools.web.controller.acrobatSign;

import com.computools.api.acrobatSign.domain.web_hooks.AgreementWorkFlowCompletedWebHookResponseDto;
import com.computools.service.acrobatSign.AcrobatWebhooksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/ia-hub-acrobat-webhook")
public class AcrobatWebhooksController {

    private static final String WEB_HOOK_SECURITY_HEADER_NAME= "x-adobesign-clientid";

    @Autowired
    private AcrobatWebhooksService acrobatWebhooksService;

    @GetMapping(value = "/{webHookUuid}")
    public ResponseEntity<String> check(
            @RequestHeader(value = WEB_HOOK_SECURITY_HEADER_NAME) String securityHeader, @PathVariable String webHookUuid) {
        log.debug("Received a header check = {}, webhook ID= {}", securityHeader, webHookUuid);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(WEB_HOOK_SECURITY_HEADER_NAME, securityHeader);
        return ResponseEntity.ok().headers(responseHeaders).build();
    }

    @PostMapping(value = "/{webhookUuid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> receive(@RequestBody AgreementWorkFlowCompletedWebHookResponseDto dto, @PathVariable String webhookUuid) {
        log.debug("Received a callback = {}, webhook ID= {}", dto, webhookUuid);
        acrobatWebhooksService.process(webhookUuid, dto);
        return ResponseEntity.ok("OK");
    }

}
