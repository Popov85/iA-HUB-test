package com.computools.service.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Oauth2ResponseDto {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("access_token")
    private String refreshToken;
    @JsonProperty("access_token")
    private String tokenType;
    @JsonProperty("access_token")
    private String expiresIn;
    @JsonProperty("access_token")
    private String apiAccessPoint;
    @JsonProperty("access_token")
    private String webAccessPoint;
}
