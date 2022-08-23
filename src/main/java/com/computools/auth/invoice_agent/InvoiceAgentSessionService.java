package com.computools.auth.invoice_agent;

import com.computools.auth.invoice_agent.domain.CurrentSessionInfoDto;
import com.computools.auth.invoice_agent.domain.RequiredCookiesDto;

public interface InvoiceAgentSessionService {
    CurrentSessionInfoDto getCurrentSessionInfo(RequiredCookiesDto requiredCookiesDto) throws Exception;
}
