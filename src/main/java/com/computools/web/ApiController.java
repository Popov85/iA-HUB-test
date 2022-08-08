package com.computools.web;

import com.computools.api.BaseUrisService;
import io.swagger.client.model.baseUris.BaseUriInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api")
public class ApiController {

    @Autowired
    private BaseUrisService baseUrisService;

    @GetMapping(value = "/baseUris")
    public ResponseEntity<BaseUriInfo> baseUris() {
        log.debug("Receive a baseUris call");
        BaseUriInfo baseUris = baseUrisService.getBaseUris();
        return ResponseEntity.ok(baseUris);
    }
}
