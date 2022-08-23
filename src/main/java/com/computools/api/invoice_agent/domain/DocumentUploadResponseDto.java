package com.computools.api.invoice_agent.domain;

import lombok.Data;

@Data
public class DocumentUploadResponseDto {
    private String id;
    private Integer pageCount;
    private Integer propertyEntityVersion;
    private Integer documentEntityVersion;
    private String direct_url;
}
