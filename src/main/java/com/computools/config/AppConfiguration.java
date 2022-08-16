package com.computools.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.http.HttpClient;
import java.time.Duration;


@Slf4j
@Configuration
public class AppConfiguration {

    private static final String DEFAULT_ACROBAT_BASE_URL = "https://api.na4.adobesign.com/oauth/v2/";

    @Bean
    @Qualifier("Acrobat")
    public URI getBaseAcrobatUri() {
        URI uri = URI.create(DEFAULT_ACROBAT_BASE_URL);
        return uri;
    }

    @Bean
    public HttpClient getHttpClient () {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(20))
                .build();
        return httpClient;
    }
}
