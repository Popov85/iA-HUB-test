package com.computools.web.controller.acrobatSign;

import com.computools.api.acrobatSign.AcrobatAgreementService;
import com.computools.api.acrobatSign.AcrobatBaseUrisService;
import com.computools.api.acrobatSign.AcrobatTransientDocumentsService;
import com.computools.api.acrobatSign.domain.AgreementCreationDto;
import com.computools.api.acrobatSign.domain.TransientDocumentDto;
import io.swagger.client.model.agreements.AgreementCreationResponse;
import io.swagger.client.model.baseUris.BaseUriInfo;
import io.swagger.client.model.transientDocuments.TransientDocumentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api")
@PreAuthorize("hasRole('ROLE_API')")
public class ApiController {

    private static final String TEMP_APP_SECURITY_HEADER_NAME = "X-iAHUB-Security-Token";

    @Autowired
    private AcrobatBaseUrisService baseUrisService;

    @Autowired
    private AcrobatTransientDocumentsService transientDocumentsService;

    @Autowired
    private AcrobatAgreementService agreementService;

    @GetMapping(value = "/baseUris")
    public ResponseEntity<BaseUriInfo> baseUris(@RequestHeader(TEMP_APP_SECURITY_HEADER_NAME) String auth) {
        log.debug("Received a baseUris call, auth = {}", auth);
        BaseUriInfo baseUris = baseUrisService.getBaseUris("Bearer "+auth);
        return ResponseEntity.ok(baseUris);
    }

    @PostMapping(value = "/transientDocument", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TransientDocumentResponse> transientDocument(@RequestHeader(TEMP_APP_SECURITY_HEADER_NAME) String auth,
                                                                       @ModelAttribute TransientDocumentDto transientDocument) {
        log.debug("Received a transientDocument call, auth = {}, transientDocument = {}", auth, transientDocument);
        TransientDocumentResponse transientDocumentResponse =
                transientDocumentsService.postTransientDocument("Bearer "+auth, transientDocument);
        return ResponseEntity.ok(transientDocumentResponse);
    }

    @PostMapping(value = "/createAndSendAgreement", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgreementCreationResponse> createAndSendAgreement(@RequestHeader(TEMP_APP_SECURITY_HEADER_NAME) String auth,
                                                                       @RequestBody AgreementCreationDto agreementCreationDto) {
        log.debug("Received a create and send agreement call, auth = {}, agreement = {}", auth, agreementCreationDto);
        AgreementCreationResponse andSendAgreement = agreementService.createAndSendAgreement("Bearer "+auth, agreementCreationDto);
        return ResponseEntity.ok(andSendAgreement);
    }

}
