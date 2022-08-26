package com.computools.auth.invoiceAgent.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AuthResponseDto {
    private String xsrfToken;
    @JsonIgnore
    private List<String> cookies;
}
