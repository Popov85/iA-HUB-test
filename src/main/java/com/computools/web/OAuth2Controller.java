package com.computools.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping(path = "/auth")
public class OAuth2Controller {

    @GetMapping
    public String getPage(@RequestParam(value = "code") String code) {
        log.debug("Receive a header check = {}", code);
        // TODO: inject code to page, complete page!
        return "login";
    }
}
