package com.computools.service;

import com.computools.service.domain.Oauth2ResponseDto;
import com.computools.service.utils.FormDataMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
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
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class Oauth2ServiceImpl implements Oauth2Service {
    @Value("${acrobat.clientId}")
    private String clientId;
    @Value("${acrobat.clientSecret}")
    private String clientSecret;
    @Autowired
    private HttpClient client;
    @Autowired
    @Qualifier("Acrobat")
    private URI acrobatBaseUri;
    @Autowired
    private FormDataMapper formDataMapper;

    @Override
    public Oauth2ResponseDto getTokens(String code) throws Exception {
        // Form parameters
        Map<Object, Object> data = new HashMap<>();
        data.put("grant_type", "code");
        data.put("code", code);
        data.put("client_id", clientId);
        data.put("client_secret", clientSecret);
        data.put("redirect_uri", null);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(formDataMapper.mapToFormData(data))
                .uri(acrobatBaseUri)
                .header("User-Agent", "iA HUB")
                .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .header("Cache-Control", CacheControl.noCache().getHeaderValue())
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Oauth2ResponseDto oauth2ResponseDto =
                new Gson().fromJson(response.body(), Oauth2ResponseDto.class);
        return oauth2ResponseDto;
    }

}
