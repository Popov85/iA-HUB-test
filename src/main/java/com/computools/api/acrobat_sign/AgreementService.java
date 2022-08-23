package com.computools.api.acrobat_sign;

import com.computools.api.acrobat_sign.domain.AgreementCreationDto;
import io.swagger.client.api.AgreementsApi;
import io.swagger.client.model.ApiClient;
import io.swagger.client.model.agreements.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AgreementService {

    @Autowired
    private ObjectFactory<ApiClient> apiClientFactory;

    public ApiClient getApiClientInstance() {
        return apiClientFactory.getObject();
    }

    public AgreementCreationResponse createAndSendAgreement(String auth, AgreementCreationDto agreementCreationDto) {
        try {//Create agreement using the transient document.
            AgreementsApi agreementsApi = new AgreementsApi(getApiClientInstance());
            AgreementCreationResponse agreementCreationResponse =
                    agreementsApi.createAgreement(auth,
                            agreementCreationDto.getAgreementCreationInfo(),
                            agreementCreationDto.getXApiUser(), agreementCreationDto.getXOnBehalfOfUser());
            return agreementCreationResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
