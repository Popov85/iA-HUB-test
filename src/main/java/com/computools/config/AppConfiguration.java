package com.computools.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.web.client.RestTemplate;

import java.net.CookieManager;
import java.net.http.HttpClient;
import java.time.Duration;


@Slf4j
@Configuration
public class AppConfiguration {

    @Value("${passwordKey}")
    private String passwordKey;

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

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.text("password", passwordKey);
    }
}
