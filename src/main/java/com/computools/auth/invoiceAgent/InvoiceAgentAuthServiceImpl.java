package com.computools.auth.invoiceAgent;

import com.computools.auth.invoiceAgent.domain.AuthDto;
import com.computools.auth.invoiceAgent.domain.AuthResponseDto;
import com.computools.auth.utility.FormDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Slf4j
@Service
@Qualifier("Java11")
@Deprecated
public class InvoiceAgentAuthServiceImpl implements InvoiceAgentAuthService {

    private static final String X_XSRF_TOKEN_HEADER_NAME = "X-XSRF-TOKEN";
    @Autowired
    private ObjectFactory<HttpClient> client;
    @Autowired
    private FormDataMapper formDataMapper;
    @Value("${invoice_agent.base_url}")
    private String baseUrl;

    @Override
    public AuthResponseDto getAuthentication(AuthDto authDto) {
        try {
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
            List<String> setCookies = response.headers().allValues(SET_COOKIE);
            List<String> xsrfToken = response.headers().allValues(X_XSRF_TOKEN_HEADER_NAME);
            if (setCookies==null || setCookies.isEmpty()
                    || xsrfToken==null || xsrfToken.isEmpty())
                throw new RuntimeException("InvoiceAgent auth. endpoint returned no required cookies!");
            AuthResponseDto result = AuthResponseDto.builder()
                    .xsrfToken(xsrfToken.get(0)).cookies(setCookies).build();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
