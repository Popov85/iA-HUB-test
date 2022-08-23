package com.computools.api.acrobat_sign.domain.web_hooks;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class AgreementWorkFlowCompletedWebHookResponseDto {
    private String actingUserEmail;
    private String actingUserId;
    private ZonedDateTime eventDate;
    private String event;
    private String eventResourceType;
    private String participantRole;
    private String participantUserEmail;
    private String participantUserId;
    private AgreementInfoWebHookResponseDto agreementInfo;
}
