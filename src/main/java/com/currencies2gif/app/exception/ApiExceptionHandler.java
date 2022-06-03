package com.currencies2gif.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiRequestCurrencyException.class)
    public ResponseEntity<Object> handleRequestCurrencyException (ApiRequestCurrencyException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                status,
                ZonedDateTime.now(ZoneId.of("Europe/Moscow"))
        );
        return new ResponseEntity<>(apiException, status);
    }

    @ExceptionHandler(value = ThirdPartyApiInvalidAnswerException.class)
    public ResponseEntity<Object> handleThirdPartyApiException (ThirdPartyApiInvalidAnswerException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException = new ApiException(
                e.getMessage(),
                status,
                ZonedDateTime.now(ZoneId.of("Europe/Moscow"))
        );
        return new ResponseEntity<>(apiException, status);
    }
}
