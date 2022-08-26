package com.computools.web.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse anException(Exception ex, WebRequest request) {
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(ex.getClass().toString(), ex.getMessage());
        log.error("An error has occurred with message = {} with cause = {} for request = {}",
                ex.getMessage(),
                ex.getCause() != null ? ex.getCause().getMessage() : "Unknown cause",
                request.toString());
        ex.printStackTrace();
        return exceptionResponse;
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse an403Exception(Exception ex, WebRequest request) {
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(ex.getClass().toString(), ex.getMessage());
        log.error("A security error has occurred with message = {} with cause = {} for request = {}",
                ex.getMessage(),
                ex.getCause() != null ? ex.getCause().getMessage() : "Unknown cause",
                request.toString());
        return exceptionResponse;
    }

}
