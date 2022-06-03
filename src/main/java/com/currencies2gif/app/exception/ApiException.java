package com.currencies2gif.app.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@RequiredArgsConstructor @Getter
public class ApiException {
    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;
}
