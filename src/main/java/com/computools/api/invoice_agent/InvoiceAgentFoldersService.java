package com.computools.api.invoice_agent;

import com.computools.api.invoice_agent.domain.FolderListDto;
import com.computools.auth.invoice_agent.domain.RequiredCookiesDto;

public interface InvoiceAgentFoldersService {
    FolderListDto getFolders(RequiredCookiesDto requiredCookiesDto);
}
