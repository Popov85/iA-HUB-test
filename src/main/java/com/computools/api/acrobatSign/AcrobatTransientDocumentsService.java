package com.computools.api.acrobatSign;

import com.computools.api.acrobatSign.domain.TransientDocumentDto;
import io.swagger.client.api.TransientDocumentsApi;
import io.swagger.client.model.ApiClient;
import io.swagger.client.model.transientDocuments.TransientDocumentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class AcrobatTransientDocumentsService {

    private static final String TEMP_FOLDER = "src/main/resources/temporary/document";

    @Autowired
    private ObjectFactory<ApiClient> apiClientFactory;

    public ApiClient getApiClientInstance() {
        return apiClientFactory.getObject();
    }

    /**
     * Post a new document (being transient)
     * @return TransientDocumentResponse object
     */
    public TransientDocumentResponse postTransientDocument(String auth, TransientDocumentDto transientDocument) {
        TransientDocumentsApi transientDocumentsApi =
                new TransientDocumentsApi(getApiClientInstance());
        Path path = null;
        try {
            path = getTempFile(transientDocument);
            TransientDocumentResponse testDocument = transientDocumentsApi
                    .createTransientDocument(auth, path.toFile(),
                            transientDocument.getXApiUser(),
                            transientDocument.getXOnBehalfOfUser(),
                            transientDocument.getFileName(), transientDocument.getMimeType());
            return testDocument;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (path!=null)
                    Files.delete(path);
                log.debug("Deleted a temp file");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    private Path getTempFile(TransientDocumentDto transientDocument) throws Exception {
        InputStream inputStream = transientDocument
                .getFile().getResource().getInputStream();
        Path path = Paths.get(TEMP_FOLDER + UUID.randomUUID());
        OutputStream output =
                new FileOutputStream(Files.createFile(path).toFile(), true);
        inputStream.transferTo(output);
        return path;
    }
}
