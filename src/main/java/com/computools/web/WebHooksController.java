package com.computools.web;

import com.computools.api.domain.web_hooks.AgreementWorkFlowCompletedWebHookResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/ia-hub-webhook")
public class WebHooksController {

    private static final String WEB_HOOK_SECURITY_HEADER_NAME= "x-adobesign-clientid";

    @GetMapping(value = "/{webHookUuid}")
    public ResponseEntity<String> check(
            @RequestHeader(value = WEB_HOOK_SECURITY_HEADER_NAME) String securityHeader, @PathVariable String webHookUuid) {
        log.debug("Receive a header check = {}, webhook ID= {}", securityHeader, webHookUuid);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(WEB_HOOK_SECURITY_HEADER_NAME, securityHeader);
        return ResponseEntity.ok().headers(responseHeaders).build();
    }

    @PostMapping(value = "/{webHookUuid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> receive(@RequestBody AgreementWorkFlowCompletedWebHookResponseDto dto, @PathVariable String webHookUuid) {
        log.debug("Receive a callback = {}, webhook ID= {}", dto, webHookUuid);
        // TODO: preform logic to
        // 1) Find config based on this webHookUuid (if not found - SecurityException)
        // 2) Log the webhook received to DynamoDb
        // 3) Retrieve and re-send the signed .pdf document to SPA
        return ResponseEntity.ok("OK");
    }

}
