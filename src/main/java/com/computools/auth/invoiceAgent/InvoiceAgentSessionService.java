package com.computools.auth.invoiceAgent;

import com.computools.auth.invoiceAgent.domain.CurrentSessionInfoDto;
import com.computools.auth.invoiceAgent.domain.RequiredCookiesDto;

public interface InvoiceAgentSessionService {
    CurrentSessionInfoDto getCurrentSessionInfo(RequiredCookiesDto requiredCookiesDto) throws Exception;
}
