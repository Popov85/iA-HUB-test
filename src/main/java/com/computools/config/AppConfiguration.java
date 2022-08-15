package com.computools.config;

import com.squareup.okhttp.OkHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class AppConfiguration {
    @Bean
    public OkHttpClient getOkHttpClient () {
        OkHttpClient okHttpClient = new OkHttpClient();
        return okHttpClient;
    }
}
