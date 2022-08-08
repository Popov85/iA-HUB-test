package com.computools.api;

import io.swagger.client.api.BaseUrisApi;
import io.swagger.client.model.ApiClient;
import io.swagger.client.model.baseUris.BaseUriInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BaseUrisService {

    @Value("${acrobat.access_token}")
    private String accessToken;

    @Autowired
    private ObjectFactory<ApiClient> apiClientFactory;

    public ApiClient getApiClientInstance() {
        return apiClientFactory.getObject();
    }

    /**
     * Get the baseUris for the user and set it in apiClient.
     * @return BaseUriInfo object
     */
    public BaseUriInfo getBaseUris() {
        String authorization = " Bearer "+accessToken;
        BaseUrisApi baseUrisApi = new BaseUrisApi(getApiClientInstance());
        try {
            BaseUriInfo baseUriInfo = baseUrisApi.getBaseUris(authorization);
            return baseUriInfo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
