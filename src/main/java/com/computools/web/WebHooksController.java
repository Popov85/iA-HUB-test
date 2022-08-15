package com.computools.web;

import com.computools.api.domain.web_hooks.AgreementWorkFlowCompletedWebHookResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/webhook")
public class WebHooksController {

    private static final String WEB_HOOK_SECURITY_HEADER_NAME= "x-adobesign-clientid";

    @GetMapping(value = "/agreement_workflow_completed")
    public ResponseEntity<String> check(@RequestHeader(value = WEB_HOOK_SECURITY_HEADER_NAME) String securityHeader) {
        log.debug("Receive a header check = {}", securityHeader);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(WEB_HOOK_SECURITY_HEADER_NAME, securityHeader);
        return ResponseEntity.ok().headers(responseHeaders).build();
    }

    // TODO: SSL protection put! PKCS12
    @PostMapping(value = "/agreement_workflow_completed", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> receive(@RequestBody AgreementWorkFlowCompletedWebHookResponseDto dto) {
        log.debug("Receive a callback = {}", dto);
        // TODO: preform logic to save the result to DynamoDB
        return ResponseEntity.ok("OK");
    }

}
