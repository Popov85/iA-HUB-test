package com.computools.web.exceptions;

import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
public class ExceptionResponse {

    private final String exception;

    private final String message;

    private final OffsetDateTime timestamp;

    public ExceptionResponse(String exception, String message) {
        this.exception = exception;
        this.message = message;
        this.timestamp = OffsetDateTime.now();
    }
}
