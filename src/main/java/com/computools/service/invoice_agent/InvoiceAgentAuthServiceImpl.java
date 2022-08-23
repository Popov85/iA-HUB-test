package com.computools.service.invoice_agent;

import com.computools.service.invoice_agent.domain.AuthDto;
import com.computools.service.utility.FormDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class InvoiceAgentAuthServiceImpl implements InvoiceAgentAuthService {
    @Autowired
    private ObjectFactory<HttpClient> client;
    @Autowired
    private FormDataMapper formDataMapper;
    @Value("${invoice_agent.base_url}")
    private String baseUrl;

    @Override
    public List<String> getAuthentication(AuthDto authDto) throws Exception {
        // Form parameters
        Map<Object, Object> data =
                Map.of("user", authDto.getUser(), "password", authDto.getPassword());
        HttpRequest request = HttpRequest.newBuilder()
                .POST(formDataMapper.mapToFormData(data))
                .uri(new URI(baseUrl+"/auth/login"))
                .header("User-Agent", "iA HUB")
                .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .header("Cache-Control", CacheControl.noCache().getHeaderValue())
                .build();
        HttpClient httpClient = client.getObject();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        List<String> setCookies = response.headers().allValues("Set-Cookie");
        log.debug("Cookies = {}", setCookies);
        return setCookies;
    }
}
