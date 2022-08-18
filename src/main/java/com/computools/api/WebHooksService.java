package com.computools.api;

import io.swagger.client.api.WebhooksApi;
import io.swagger.client.model.ApiClient;
import io.swagger.client.model.webhooks.WebhookAgreementEvents;
import io.swagger.client.model.webhooks.WebhookConditionalParams;
import io.swagger.client.model.webhooks.WebhookCreationResponse;
import io.swagger.client.model.webhooks.WebhookInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class WebHooksService {
    @Autowired
    private ObjectFactory<ApiClient> apiClientFactory;

    public ApiClient getApiClientInstance() {
        return apiClientFactory.getObject();
    }

    /**
     * GCreate a web hook programmatically
     * @return WebhookCreationResponse webhook created response
     */
    public WebhookCreationResponse createWebHook(String token) {
        WebhooksApi webhooksApi = new WebhooksApi(getApiClientInstance());
        try {
            WebhookInfo webhookInfo = new WebhookInfo();
            webhookInfo.setName("Test web-hook name");
            webhookInfo.setScope(WebhookInfo.ScopeEnum.ACCOUNT);
            webhookInfo.setState(WebhookInfo.StateEnum.ACTIVE);
            webhookInfo.setWebhookSubscriptionEvents(
                    Arrays.asList(WebhookInfo.WebhookSubscriptionEventsEnum.AGREEMENT_ALL));
            WebhookConditionalParams webhookConditionalParams = new WebhookConditionalParams();
            webhookConditionalParams.setWebhookAgreementEvents(
                    new WebhookAgreementEvents().includeSignedDocuments(true));
            webhookInfo.setWebhookConditionalParams(webhookConditionalParams);
            WebhookCreationResponse webhook =
                    webhooksApi.createWebhook(token, webhookInfo, null, null);
            return webhook;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
