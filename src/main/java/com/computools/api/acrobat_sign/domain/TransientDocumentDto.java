package com.computools.api.acrobat_sign.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class TransientDocumentDto {
    @NotNull
    private MultipartFile file;
    private String xApiUser;
    private String xOnBehalfOfUser;
    private String fileName;
    private String mimeType;

    @Override
    public String toString() {
        return "TransientDocument{" +
                "file size=" + (file!=null ? file.getSize() : 0) +
                ", xApiUser='" + xApiUser + '\'' +
                ", xOnBehalfOfUser='" + xOnBehalfOfUser + '\'' +
                ", fileName='" + fileName + '\'' +
                ", mimeType='" + mimeType + '\'' +
                '}';
    }
}
