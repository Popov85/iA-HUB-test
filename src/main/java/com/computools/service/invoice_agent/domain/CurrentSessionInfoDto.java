package com.computools.service.invoice_agent.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
