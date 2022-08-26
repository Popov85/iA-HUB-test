package com.computools.auth.invoiceAgent;

import com.computools.auth.invoiceAgent.domain.AuthDto;
import com.computools.auth.invoiceAgent.domain.AuthResponseDto;
import com.computools.config.APIConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Slf4j
@Service
@Qualifier("Spring")
public class InvoiceAgentAuthServiceSpringImpl implements InvoiceAgentAuthService {

    private static final String X_XSRF_TOKEN_HEADER_NAME = "X-XSRF-TOKEN";

    @Autowired
    private APIConfiguration apiConfiguration;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public AuthResponseDto getAuthentication(AuthDto authDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "iA HUB");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> credentialsMap =
                new LinkedMultiValueMap<>(Map.of(
                        "user", Collections.singletonList(authDto.getUser()),
                        "password", Collections.singletonList(authDto.getPassword())));

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(credentialsMap, headers);

        ResponseEntity<String> response =
                restTemplate.exchange(apiConfiguration.INVOICE_AGENT_LOGIN, HttpMethod.POST, entity, String.class);
        if (!response.getStatusCode().equals(HttpStatus.OK))
            throw new RuntimeException("Failed to auth. against InvoiceAgent, message = " + response.getBody());
        List<String> setCookies =
                response.getHeaders().get(SET_COOKIE);
        List<String> xsrfToken =
                response.getHeaders().get(X_XSRF_TOKEN_HEADER_NAME);
        if (setCookies==null || setCookies.isEmpty()
                || xsrfToken==null || xsrfToken.isEmpty())
            throw new RuntimeException("InvoiceAgent auth. endpoint returned no required cookies!");

        return AuthResponseDto.builder()
                .xsrfToken(xsrfToken.get(0))
                .cookies(setCookies)
                .build();
    }
}
