package com.computools.auth.invoice_agent;

import com.computools.auth.invoice_agent.domain.CurrentSessionInfoDto;
import com.computools.auth.invoice_agent.domain.RequiredCookiesDto;
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
public class InvoiceAgentSessionServiceImpl implements InvoiceAgentSessionService {

    @Value("${invoice_agent.base_url}")
    private String baseUrl;
    @Autowired
    private ObjectFactory<HttpClient> client;

    @Override
    public CurrentSessionInfoDto getCurrentSessionInfo(RequiredCookiesDto requiredCookiesDto) throws Exception {
        HttpRequest sessionRequest = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(baseUrl + "/sessions_v2/current"))
                .header("Accept", MediaType.APPLICATION_JSON_VALUE)
                .header("X-Requested-With", "iA HUB")
                .header("Cache-Control", CacheControl.noCache().getHeaderValue())
                .header("Cookie", requiredCookiesDto.getCookies())
                .build();
        HttpResponse<String> sessionResponse =
                client.getObject().send(sessionRequest, HttpResponse.BodyHandlers.ofString());
        if (sessionResponse.statusCode()!=200)
            throw new RuntimeException("Response is not 200 " + sessionResponse);
        CurrentSessionInfoDto currentSessionInfoDto =
                new Gson().fromJson(sessionResponse.body(), CurrentSessionInfoDto.class);
        log.debug("Session data = {}", currentSessionInfoDto);
        return currentSessionInfoDto;
    }
}
