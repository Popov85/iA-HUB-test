package com.computools.service;

import com.computools.service.domain.Oauth2ResponseDto;

public interface Oauth2Service {
    Oauth2ResponseDto getTokens(String code) throws Exception;
}
