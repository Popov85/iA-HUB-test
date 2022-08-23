package com.computools.auth.invoice_agent.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequiredCookiesDto {
    private String jSID;
    private String aWsAlb;
    private String aWSAlbCORS;
    private String xsrfToken;

    public String getCookies() {
        return "JSESSIONID=" + jSID + ";XSRF-TOKEN=" + xsrfToken + ";AWSALB=" + aWsAlb + ";AWSALBCORS=" + aWSAlbCORS;
    }
}
