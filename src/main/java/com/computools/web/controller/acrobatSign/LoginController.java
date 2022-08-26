package com.computools.web.controller.acrobatSign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping(path = "/login")
public class LoginController {
    private static final String TEMP_REDIRECT_URI= "https://oauth.pstmn.io/v1/callback";
    @Value("${acrobat.clientId}")
    private String clientId;

    @Value("${acrobat.default_scopes}")
    private List<String> defaultScopes;

    @GetMapping
    public ModelAndView getLoginPage() {
        log.debug("Return login page");
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("redirect_uri", TEMP_REDIRECT_URI);
        modelAndView.addObject("response_type", "code");
        modelAndView.addObject("client_id", clientId);
        modelAndView.addObject("scope",
                defaultScopes.stream().collect(Collectors.joining(" ")));
        return modelAndView;
    }
}
