package com.computools.api.invoice_agent;

import com.computools.api.invoice_agent.domain.DocumentUploadResponseDto;
import com.computools.auth.invoice_agent.domain.RequiredCookiesDto;
import org.springframework.web.multipart.MultipartFile;

public interface InvoiceAgentArchiveService {
    DocumentUploadResponseDto upload(RequiredCookiesDto requiredCookiesDto, String xsrfTokenHeader, MultipartFile file);
}
