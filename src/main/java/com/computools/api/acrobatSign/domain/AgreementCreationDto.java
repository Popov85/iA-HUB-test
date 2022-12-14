package com.computools.api.acrobatSign.domain;

import io.swagger.client.model.agreements.AgreementCreationInfo;
import lombok.Data;

@Data
public class AgreementCreationDto {
    private AgreementCreationInfo agreementCreationInfo;
    private String xApiUser;
    private String xOnBehalfOfUser;
}
