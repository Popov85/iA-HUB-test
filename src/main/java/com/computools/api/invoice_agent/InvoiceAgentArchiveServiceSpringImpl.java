package com.computools.api.invoice_agent;

import com.computools.api.invoice_agent.domain.DocumentUploadResponseDto;
import com.computools.config.APIConfiguration;
import com.computools.auth.invoice_agent.domain.RequiredCookiesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Qualifier("Spring")
public class InvoiceAgentArchiveServiceSpringImpl implements InvoiceAgentArchiveService {
    @Autowired
    private APIConfiguration apiConfiguration;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public DocumentUploadResponseDto upload(RequiredCookiesDto requiredCookiesDto, String xsrfTokenHeader, MultipartFile file){
        try {
            LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("file", new InputStreamResource(file.getInputStream(), file.getOriginalFilename()));
            map.add("path", "/test123");
            map.add("name", file.getOriginalFilename());
            map.add("overwrite", "true");
            map.add("overwriteUpdate", "true");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.add("X-XSRF-TOKEN", xsrfTokenHeader);
            headers.add("X-Requested-With", "iA HUB");
            headers.add("Cookie", requiredCookiesDto.getCookies());

            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);

            DocumentUploadResponseDto response = restTemplate
                    .postForObject(apiConfiguration.INVOICE_AGENT_ARCHIVE_UPLOAD, requestEntity, DocumentUploadResponseDto.class);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
