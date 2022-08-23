package com.computools.web;

import com.computools.api.invoice_agent.InvoiceAgentArchiveService;
import com.computools.api.invoice_agent.InvoiceAgentFoldersService;
import com.computools.api.invoice_agent.domain.DocumentUploadResponseDto;
import com.computools.api.invoice_agent.domain.FolderListDto;
import com.computools.auth.invoice_agent.InvoiceAgentAuthService;
import com.computools.auth.invoice_agent.InvoiceAgentSessionService;
import com.computools.auth.invoice_agent.domain.AuthDto;
import com.computools.auth.invoice_agent.domain.CurrentSessionInfoDto;
import com.computools.auth.invoice_agent.domain.RequiredCookiesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/invoiceAgent")
public class InvoiceAgentController {

    @Autowired
    @Qualifier("Spring")
    private InvoiceAgentAuthService invoiceAgentAuthService;

    @Autowired
    @Qualifier("Spring")
    private InvoiceAgentFoldersService invoiceAgentFoldersService;

    @Autowired
    @Qualifier("Spring")
    private InvoiceAgentSessionService invoiceAgentSessionService;

    @Autowired
    @Qualifier("Spring")
    private InvoiceAgentArchiveService invoiceAgentArchiveService;

    @PostMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAuthenticated(@RequestBody AuthDto authDto) throws Exception {
        List<String> setCookies = invoiceAgentAuthService.getAuthentication(authDto);
        log.debug("setCookies = {}", setCookies);
        HttpHeaders httpHeaders = new HttpHeaders();
        setCookies
                .forEach(cookie -> httpHeaders.add(HttpHeaders.SET_COOKIE, cookie));
        return new ResponseEntity(httpHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/session", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CurrentSessionInfoDto> getCurrentSession(
            @CookieValue(value = "JSESSIONID", required = true) String jSID,
            @CookieValue(value = "AWSALB", required = true) String aWsAlb,
            @CookieValue(value = "AWSALBCORS", required = true) String aWSAlbCORS,
            @CookieValue(value = "XSRF-TOKEN", required = true) String xsrfToken) throws Exception {
        RequiredCookiesDto requiredCookiesDto = RequiredCookiesDto
                .builder()
                .jSID(jSID)
                .xsrfToken(xsrfToken)
                .aWsAlb(aWsAlb)
                .aWSAlbCORS(aWSAlbCORS)
                .build();
        log.debug("RequiredCookiesDto = {}", requiredCookiesDto);
        CurrentSessionInfoDto currentSessionInfo =
                invoiceAgentSessionService.getCurrentSessionInfo(requiredCookiesDto);
        log.debug("Session = {}", currentSessionInfo);
        return ResponseEntity.ok(currentSessionInfo);
    }

    @GetMapping(value = "/folders", produces = MediaType.APPLICATION_JSON_VALUE)
    public FolderListDto getFolders(
            @CookieValue(value = "JSESSIONID", required = true) String jSID,
            @CookieValue(value = "AWSALB", required = true) String aWsAlb,
            @CookieValue(value = "AWSALBCORS", required = true) String aWSAlbCORS,
            @CookieValue(value = "XSRF-TOKEN", required = true) String xsrfToken) throws Exception {
        RequiredCookiesDto requiredCookiesDto = RequiredCookiesDto
                .builder()
                .jSID(jSID)
                .xsrfToken(xsrfToken)
                .aWsAlb(aWsAlb)
                .aWSAlbCORS(aWSAlbCORS)
                .build();
        FolderListDto result =
                invoiceAgentFoldersService.getFolders(requiredCookiesDto);
        log.debug("Find all folders = {}", result);
        return result;
    }

    @PostMapping(value = "/archive", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DocumentUploadResponseDto putToArchive(
            @CookieValue(value = "JSESSIONID", required = true) String jSID,
            @CookieValue(value = "AWSALB", required = true) String aWsAlb,
            @CookieValue(value = "AWSALBCORS", required = true) String aWSAlbCORS,
            @CookieValue(value = "XSRF-TOKEN", required = true) String xsrfToken,
            @RequestHeader(value = "X-XSRF-TOKEN") String xsrfTokenHeader,
            @RequestPart MultipartFile file) throws Exception {
        RequiredCookiesDto requiredCookiesDto = RequiredCookiesDto
                .builder()
                .jSID(jSID)
                .xsrfToken(xsrfToken)
                .aWsAlb(aWsAlb)
                .aWSAlbCORS(aWSAlbCORS)
                .build();
        log.debug("Start uploading, RequiredCookiesDto = {}", requiredCookiesDto);
        DocumentUploadResponseDto documentUploadResponseDto =
                invoiceAgentArchiveService.upload(requiredCookiesDto, xsrfTokenHeader, file);
        log.debug("Uploaded to archive ");
        return documentUploadResponseDto;
    }
}
