package com.computools.api.invoiceAgent;

import com.computools.api.invoiceAgent.domain.DocumentUploadResponseDto;
import com.computools.config.APIConfiguration;
import com.computools.auth.invoiceAgent.domain.RequiredCookiesDto;
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
import org.springframework.web.util.WebUtils;

import javax.validation.constraints.NotNull;
import java.net.HttpCookie;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@Qualifier("Spring")
public class InvoiceAgentArchiveServiceSpringImpl implements InvoiceAgentArchiveService {
    @Autowired
    private APIConfiguration apiConfiguration;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public DocumentUploadResponseDto upload(final @NotNull RequiredCookiesDto requiredCookiesDto,
                                            final @NotNull String xsrfTokenHeader,
                                            final @NotNull MultipartFile file){
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

    @Override
    public DocumentUploadResponseDto upload(final @NotNull List<String> requiredCookiesDto,
                                            final @NotNull String xsrfTokenHeader,
                                            final @NotNull MultipartFile file){
        try {
            LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("file", new InputStreamResource(file.getInputStream(), file.getOriginalFilename()));
            map.add("path", "/test123");
            map.add("name", "cheque.pdf");
            map.add("overwrite", "true");
            map.add("overwriteUpdate", "true");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.add("X-XSRF-TOKEN", xsrfTokenHeader);
            headers.add("X-Requested-With", "iA HUB");

            String cookies = requiredCookiesDto.stream()
                    .flatMap(c -> HttpCookie.parse(c).stream())
                    .map(c -> c.getName() + "=" + c.getValue())
                    .collect(Collectors.joining(";"));

            headers.add("Cookie", cookies);

            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);

            DocumentUploadResponseDto response = restTemplate
                    .postForObject(apiConfiguration.INVOICE_AGENT_ARCHIVE_UPLOAD, requestEntity, DocumentUploadResponseDto.class);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
