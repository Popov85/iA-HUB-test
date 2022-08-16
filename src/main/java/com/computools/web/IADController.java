package com.computools.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/invoiceAgent")
public class IADController {

    @GetMapping("/")
    public List<Void> get() {
        log.debug("Find all = {}");
        return Collections.emptyList();
    }
}
