package com.computools.api.invoiceAgent;

import com.computools.api.invoiceAgent.domain.FolderListDto;
import com.computools.auth.invoiceAgent.domain.RequiredCookiesDto;
import com.google.gson.Gson;
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

@Slf4j
@Service
@Qualifier("Java11")
@Deprecated
public class InvoiceAgentFoldersServiceImpl implements InvoiceAgentFoldersService {
    @Value("${invoice_agent.base_url}")
    private String baseUrl;

    @Autowired
    private ObjectFactory<HttpClient> client;

    @Override
    public FolderListDto getFolders(RequiredCookiesDto requiredCookiesDto) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI(baseUrl+"/folders_v4/1/list"))
                    .header("Accept", MediaType.APPLICATION_JSON_VALUE)
                    .header("X-XSRF-TOKEN", requiredCookiesDto.getXsrfToken())
                    .header("X-Requested-With", "iA HUB")
                    .header("Cache-Control", CacheControl.noCache().getHeaderValue())
                    .header("Cookie", requiredCookiesDto.getCookies())
                    .build();
            HttpResponse<String> response =
                    client.getObject().send(request, HttpResponse.BodyHandlers.ofString());
            FolderListDto result =
                    new Gson().fromJson(response.body(), FolderListDto.class);
            log.debug("Result = {}", result);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
