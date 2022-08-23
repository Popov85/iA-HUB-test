package com.computools.service.invoice_agent.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequiredCookiesDto {
    private String jSID;
    private String aWsAlb;
    private String aWSAlbCORS;
    private String xsrfToken;
}
