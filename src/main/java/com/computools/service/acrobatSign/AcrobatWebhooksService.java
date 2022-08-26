package com.computools.service.acrobatSign;

import com.computools.api.acrobatSign.domain.web_hooks.AgreementWorkFlowCompletedWebHookResponseDto;

public interface AcrobatWebhooksService {
    void process(String webHookUuid, AgreementWorkFlowCompletedWebHookResponseDto dto);
}
