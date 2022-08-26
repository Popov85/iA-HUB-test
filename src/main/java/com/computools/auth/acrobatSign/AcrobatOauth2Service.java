package com.computools.auth.acrobatSign;

import com.computools.auth.acrobatSign.domain.Oauth2ResponseDto;

public interface AcrobatOauth2Service {
    Oauth2ResponseDto getTokens(String code) throws Exception;
}
