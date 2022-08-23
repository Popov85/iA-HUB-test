package com.computools.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.net.CookieManager;
import java.net.http.HttpClient;
import java.time.Duration;


@Slf4j
@Configuration
public class AppConfiguration {
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public HttpClient getHttpClient () {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(20))
                .cookieHandler(new CookieManager())
                .build();
        return httpClient;
    }
}
