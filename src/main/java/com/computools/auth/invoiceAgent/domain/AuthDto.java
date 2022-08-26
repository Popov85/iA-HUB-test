package com.computools.auth.invoiceAgent.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    @NotNull
    private String user;
    @NotNull
    private String password;
}
