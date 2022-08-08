package com.computools.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/callback")
public class WebHooksController {

    private static final String WEB_HOOK_SECURITY_HEADER_NAME= "x-adobesign-clientid";

    @GetMapping(value = "/1")
    public ResponseEntity<String> check(@RequestHeader(value = WEB_HOOK_SECURITY_HEADER_NAME) String securityHeader) {
        log.debug("Receive a header check = {}", securityHeader);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(WEB_HOOK_SECURITY_HEADER_NAME, securityHeader);
        return ResponseEntity.ok().headers(responseHeaders).build();
    }

    @PostMapping(value = "/1", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> receive(@RequestBody Object dto) {
        log.debug("Receive a callback = {}", dto);
        return ResponseEntity.ok("OK");
    }

}
