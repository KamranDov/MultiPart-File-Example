package com.crocusoft.multipartfile.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ExceptionResponse(
        LocalDateTime timestamp,
        Integer statusCode,
        HttpStatus statusName,
        String message
) {
}
