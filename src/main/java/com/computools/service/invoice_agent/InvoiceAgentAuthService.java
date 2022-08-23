package com.computools.service.invoice_agent;

import com.computools.service.invoice_agent.domain.AuthDto;

import java.util.List;

public interface InvoiceAgentAuthService {
    List<String> getAuthentication(AuthDto authDto) throws Exception;
}
