package com.computools.config;

import io.swagger.client.model.ApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Slf4j
@Configuration
public class AcrobatConfiguration {
    @Value("${acrobat.base_api_url}")
    private String baseApiUrl;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ApiClient getApiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(baseApiUrl);
        apiClient.setConnectTimeout(10000);
        return apiClient;
    }
}
