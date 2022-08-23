package com.computools.api.invoice_agent;

import com.computools.api.invoice_agent.domain.DocumentUploadResponseDto;
import com.computools.service.invoice_agent.domain.RequiredCookiesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class InvoiceAgentArchiveServiceSpring {
    @Value("${invoice_agent.base_url}")
    private String baseUrl;

    public DocumentUploadResponseDto upload(RequiredCookiesDto requiredCookiesDto, String xsrfTokenHeader, MultipartFile file) throws Exception{
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        DocumentUploadResponseDto response;
        try {
            map.add("file", new InputStreamResource(file.getInputStream(), file.getOriginalFilename()));
            map.add("path", "/test123");
            map.add("name", "Roofing_contract_via_API_2.pdf");
            map.add("overwrite", "true");
            map.add("overwriteUpdate", "true");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.add("X-XSRF-TOKEN", xsrfTokenHeader);
            headers.add("X-Requested-With", "iA HUB");
            headers.add("Cookie",
                    "JSESSIONID=" + requiredCookiesDto.getJSID() +
                            ";XSRF-TOKEN=" + requiredCookiesDto.getXsrfToken() +
                            ";AWSALB=" + requiredCookiesDto.getAWsAlb() +
                            ";AWSALBCORS=" + requiredCookiesDto.getAWSAlbCORS());

            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);

            response = new RestTemplate()
                    .postForObject(baseUrl+"/archives_v3", requestEntity, DocumentUploadResponseDto.class);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
