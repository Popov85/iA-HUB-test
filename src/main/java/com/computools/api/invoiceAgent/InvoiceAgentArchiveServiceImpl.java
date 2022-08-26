package com.computools.api.invoiceAgent;

import com.computools.api.invoiceAgent.domain.DocumentUploadResponseDto;
import com.computools.auth.invoiceAgent.domain.RequiredCookiesDto;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
@Qualifier("Java11")
@Deprecated
public class InvoiceAgentArchiveServiceImpl implements InvoiceAgentArchiveService {
    @Value("${invoice_agent.base_url}")
    private String baseUrl;

    @Autowired
    private ObjectFactory<HttpClient> client;

    @Override
    public DocumentUploadResponseDto upload(RequiredCookiesDto requiredCookiesDto, String xsrfTokenHeader, MultipartFile file){
        try {
            InputStream inputStream = file.getInputStream();

            /**
             * Create a Multipart request body with MultipartEntityBuilder.
             */
            HttpEntity httpEntity = MultipartEntityBuilder.create()
                    // FORM
                    .addPart("path",
                            new StringBody("/test123",
                                    ContentType.create("multipart/form-data", StandardCharsets.UTF_8)))
                    .addPart("overwrite", new StringBody("true",
                            ContentType.create("multipart/form-data", StandardCharsets.UTF_8)))
                    .addPart("overwriteUpdate", new StringBody("true",
                            ContentType.create("multipart/form-data", StandardCharsets.UTF_8)))
                    .addPart("name",
                            new StringBody("Roofing_contract_via_API_2.pdf",
                                    ContentType.create("multipart/form-data", StandardCharsets.UTF_8)))
                    // FILE
                    .addPart("file", new InputStreamBody(inputStream,"Roofing_contract_via_API_2.pdf"))
                    .build();

            /**
             * Use pipeline streams to write the encoded data directly to the network
             * instead of caching it in memory. Because Multipart request bodies contain
             * files, they can cause memory overflows if cached in memory.
             */
            Pipe pipe = Pipe.open();

            // Pipeline streams must be used in a multi-threaded environment. Using one
            // thread for simultaneous reads and writes can lead to deadlocks.
            new Thread(() -> {
                try (OutputStream outputStream = Channels.newOutputStream(pipe.sink())) {
                    // Write the encoded data to the pipeline.
                    httpEntity.writeTo(outputStream);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();

            HttpRequest request = HttpRequest
                    .newBuilder(new URI(baseUrl+"/archives_v3"))
                    // The Content-Type header is important, don't forget to set it.
                    .header("Content-Type", httpEntity.getContentType().getValue())
                    .header("X-XSRF-TOKEN", xsrfTokenHeader)
                    .header("X-Requested-With", "iA HUB")
                    .header("Cookie",
                            "JSESSIONID=" + requiredCookiesDto.getJSID() +
                                    ";XSRF-TOKEN=" + requiredCookiesDto.getXsrfToken() +
                                    ";AWSALB=" + requiredCookiesDto.getAWsAlb() +
                                    ";AWSALBCORS=" + requiredCookiesDto.getAWSAlbCORS())
                    // Reads data from a pipeline stream.
                    .POST(HttpRequest.BodyPublishers.ofInputStream(() -> Channels.newInputStream(pipe.source()))).build();

            HttpResponse<String> responseBody = client.getObject().send(request,
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (responseBody.statusCode()!=200)
                throw new RuntimeException("Request has not been successful, message = "+responseBody);
            DocumentUploadResponseDto result =
                    new Gson().fromJson(responseBody.body(), DocumentUploadResponseDto.class);
            log.debug("DocumentUploadResponseDto = {}", result);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DocumentUploadResponseDto upload(List<String> requiredCookiesDto, String xsrfTokenHeader, MultipartFile file) {
        throw new UnsupportedOperationException("TO BE impl.");
    }

}
