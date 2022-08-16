package com.computools.web;

import com.computools.service.Oauth2Service;
import com.computools.service.domain.Oauth2ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/oauth2")
public class OAuth2Controller {
    @Autowired
    private Oauth2Service oauth2Service;

    @GetMapping
    public ResponseEntity<Oauth2ResponseDto> receiveCodeAndGetTokens(@RequestParam(value = "code") String code) throws Exception {
        // TODO: put token info to http session, or DynamoDb (return only ID)
        log.debug("Receive a header check = {}", code);
        return ResponseEntity.ok(oauth2Service.getTokens(code));
    }
}
