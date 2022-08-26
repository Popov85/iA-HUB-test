package com.computools.service.acrobatSign;

import com.computools.api.acrobatSign.domain.web_hooks.AgreementWorkFlowCompletedWebHookResponseDto;
import com.computools.api.invoiceAgent.InvoiceAgentArchiveService;
import com.computools.api.invoiceAgent.domain.DocumentUploadResponseDto;
import com.computools.auth.invoiceAgent.InvoiceAgentAuthService;
import com.computools.auth.invoiceAgent.domain.AuthDto;
import com.computools.auth.invoiceAgent.domain.AuthResponseDto;
import com.computools.service.appEvents.IaHubEventPublisher;
import com.computools.service.iaHub.WebhooksService;
import com.computools.service.iaHub.dto.WebhooksDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Slf4j
@Service
public class AcrobatWebhooksServiceImpl implements AcrobatWebhooksService {

    @Autowired
    private WebhooksService webhooksService;

    @Autowired
    private IaHubEventPublisher iaHubEventPublisher;

    @Autowired
    @Qualifier("Spring")
    private InvoiceAgentAuthService invoiceAgentAuthService;
    @Autowired
    @Qualifier("Spring")
    private InvoiceAgentArchiveService invoiceAgentArchiveService;

    @Override
    public void process(final @NotNull String webhookUuid,
                        final @NotNull AgreementWorkFlowCompletedWebHookResponseDto dto) {
        // TODO: 1) Extract config from Webhooks table by webhookUuid
        // TODO: 2) Authenticate against invoiceAgent and obtain all cookies and xsrfToken
        // TODO: 3) Based on config send signed document to invoiceAgent;
        // TODO: 4) Publish an event about Logs (to save item to Logs table)!
        try {
            WebhooksDto webhooksDto = webhooksService.findByHashKey(webhookUuid)
                    .orElseThrow(() -> new SecurityException("Webhook not found! webhookId="+webhookUuid));

            Map<String, String> credentials = webhooksDto.getCredentials();

            log.debug("Cred = {}", credentials);

            // Get list of cookies, xsrfToken
            AuthResponseDto authentication = invoiceAgentAuthService.
                    getAuthentication(new AuthDto(credentials.get("user"), credentials.get("password")));

            DocumentUploadResponseDto documentUploadResponseDto =
                    invoiceAgentArchiveService.upload(authentication.getCookies(), authentication.getXsrfToken(), null);

            // TODO: log success event!
            //iaHubEventPublisher.publishLogsEvent(null);
        } catch (Exception e) {
            // TODO: log failure event!
            log.error("Acrobat webhook processing failed, message = {}", e.getMessage());
            //iaHubEventPublisher.publishLogsEvent(null);
        }

    }
}
