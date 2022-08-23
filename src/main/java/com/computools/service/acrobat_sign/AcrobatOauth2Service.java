package com.computools.service.acrobat_sign;

import com.computools.service.acrobat_sign.domain.Oauth2ResponseDto;

public interface AcrobatOauth2Service {
    Oauth2ResponseDto getTokens(String code) throws Exception;
}
