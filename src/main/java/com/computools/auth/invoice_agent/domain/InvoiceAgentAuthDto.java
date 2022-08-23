package com.computools.auth.invoice_agent.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class InvoiceAgentAuthDto {
    private String tenantId;
    private String sessionTicketId;
    private String userName;
    private String userId;
    private String domainName;
    private String domainId;
    private String fullName;
    private String autoLogin;
    private String appendix;
    private String xsrfToken;
    private List<String> setCookies;
}
