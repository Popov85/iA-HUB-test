package com.computools.api.invoiceAgent;

import com.computools.api.invoiceAgent.domain.FolderListDto;
import com.computools.auth.invoiceAgent.domain.RequiredCookiesDto;

public interface InvoiceAgentFoldersService {
    FolderListDto getFolders(RequiredCookiesDto requiredCookiesDto);
}
