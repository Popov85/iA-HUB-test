package com.computools.auth.invoice_agent.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrentSessionInfoDto {
    private String tenantId;
    private String sessionTicketId;
    private String userName;
    private String userId;
    private String domainName;
    private String domainId;
    private String fullName;
    private String autoLogin;
    private String appendix;
}
