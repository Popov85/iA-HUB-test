package com.computools.auth.acrobat_sign;

import com.computools.auth.acrobat_sign.domain.Oauth2ResponseDto;

public interface AcrobatOauth2Service {
    Oauth2ResponseDto getTokens(String code) throws Exception;
}
