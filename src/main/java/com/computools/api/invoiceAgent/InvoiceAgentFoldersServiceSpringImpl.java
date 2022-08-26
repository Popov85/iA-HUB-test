package com.computools.api.invoiceAgent;

import com.computools.api.invoiceAgent.domain.FolderListDto;
import com.computools.config.APIConfiguration;
import com.computools.auth.invoiceAgent.domain.RequiredCookiesDto;
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
public class InvoiceAgentFoldersServiceSpringImpl implements InvoiceAgentFoldersService {
    @Autowired
    private APIConfiguration apiConfiguration;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public FolderListDto getFolders(RequiredCookiesDto requiredCookiesDto) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("X-Requested-With", "iA HUB");
            headers.add("Cookie", requiredCookiesDto.getCookies());
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

            ResponseEntity<FolderListDto> response = restTemplate.exchange(
                    apiConfiguration.INVOICE_AGENT_FOLDER_LIST, HttpMethod.GET, entity, FolderListDto.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
