package com.computools.auth.invoice_agent;

import com.computools.config.APIConfiguration;
import com.computools.auth.invoice_agent.domain.AuthDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@Qualifier("Spring")
public class InvoiceAgentAuthServiceSpringImpl implements InvoiceAgentAuthService {

    @Autowired
    private APIConfiguration apiConfiguration;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<String> getAuthentication(AuthDto authDto) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("User-Agent", "iA HUB");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("user", authDto.getUser());
        map.add("password", authDto.getPassword());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<String> response =
                restTemplate.exchange(apiConfiguration.INVOICE_AGENT_LOGIN, HttpMethod.POST, entity, String.class);
        List<String> setCookies =
                response.getHeaders().get("Set-Cookie");
        return setCookies;
    }
}
