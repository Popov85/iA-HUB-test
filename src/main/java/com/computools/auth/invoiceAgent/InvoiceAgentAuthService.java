package com.computools.auth.invoiceAgent;

import com.computools.auth.invoiceAgent.domain.AuthDto;
import com.computools.auth.invoiceAgent.domain.AuthResponseDto;

public interface InvoiceAgentAuthService {
    AuthResponseDto getAuthentication(AuthDto authDto);
}
