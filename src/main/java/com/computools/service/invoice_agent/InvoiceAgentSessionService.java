package com.computools.service.invoice_agent;

import com.computools.service.invoice_agent.domain.CurrentSessionInfoDto;
import com.computools.service.invoice_agent.domain.RequiredCookiesDto;

public interface InvoiceAgentSessionService {
    CurrentSessionInfoDto getCurrentSessionInfo(RequiredCookiesDto requiredCookiesDto) throws Exception;
}
