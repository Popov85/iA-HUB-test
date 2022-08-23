package com.computools.auth.invoice_agent;

import com.computools.config.APIConfiguration;
import com.computools.auth.invoice_agent.domain.CurrentSessionInfoDto;
import com.computools.auth.invoice_agent.domain.RequiredCookiesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Qualifier("Spring")
public class InvoiceAgentSessionServiceSpringImpl implements InvoiceAgentSessionService {

    @Autowired
    private APIConfiguration apiConfiguration;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public CurrentSessionInfoDto getCurrentSessionInfo(RequiredCookiesDto requiredCookiesDto) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("X-Requested-With", "iA HUB");
        headers.add("Cookie", requiredCookiesDto.getCookies());
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        ResponseEntity<CurrentSessionInfoDto> response = restTemplate.exchange(
                apiConfiguration.INVOICE_AGENT_SESSION, HttpMethod.GET, entity, CurrentSessionInfoDto.class);

        return response.getBody();
    }
}
