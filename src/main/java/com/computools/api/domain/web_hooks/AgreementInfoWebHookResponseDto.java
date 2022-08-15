package com.computools.api.domain.web_hooks;

import io.swagger.client.model.agreements.AgreementInfo;
import lombok.Data;

import java.util.List;

@Data
public class AgreementInfoWebHookResponseDto extends AgreementInfo {

    @Data
    public static class DocumentInfo {
        private String id;
        private String mimeType;
        private String name;
        private int numPages;
    }

    @Data
    public static class SignedDocumentInfo {
        private String document;
    }

    private List<Object> documentsInfo;

    private List<Object> signedDocumentInfo;
}
