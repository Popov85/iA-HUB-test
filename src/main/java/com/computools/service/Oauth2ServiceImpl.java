package com.computools.service;

import com.computools.service.domain.Oauth2ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Oauth2ServiceImpl implements Oauth2Service {
    private static final String TEMP_BASE_TOKEN_URL = "https://api.na4.adobesign.com/oauth/v2/token";
    @Value("${acrobat.clientId}")
    private String clientId;
    @Value("${acrobat.clientSecret}")
    private String clientSecret;
    @Autowired
    private OkHttpClient client;

    @Override
    public Oauth2ResponseDto getTokens(String code) {
        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "code")
                .add("code", code)
                .add("client_id", clientId)
                .add("client_secret", clientSecret)
                .add("redirect_uri", null)
                .build();
        Request request = new Request.Builder()
                .url(TEMP_BASE_TOKEN_URL)
                .post(formBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new Exception("Unexpected code " + response);
            Oauth2ResponseDto oauth2ResponseDto =
                    new ObjectMapper().readValue(response.body().string(), Oauth2ResponseDto.class);
            log.debug("Oauth2ResponseDto = {}", oauth2ResponseDto);
            return oauth2ResponseDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
