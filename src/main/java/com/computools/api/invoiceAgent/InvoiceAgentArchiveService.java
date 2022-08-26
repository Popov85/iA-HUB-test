package com.computools.api.invoiceAgent;

import com.computools.api.invoiceAgent.domain.DocumentUploadResponseDto;
import com.computools.auth.invoiceAgent.domain.RequiredCookiesDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface InvoiceAgentArchiveService {
    DocumentUploadResponseDto upload(RequiredCookiesDto requiredCookiesDto, String xsrfTokenHeader, MultipartFile file);
    DocumentUploadResponseDto upload(List<String> requiredCookiesDto, String xsrfTokenHeader, MultipartFile file);
}
