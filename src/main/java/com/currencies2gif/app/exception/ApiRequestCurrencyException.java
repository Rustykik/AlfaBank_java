package com.currencies2gif.app.exception;

public class ApiRequestCurrencyException extends RuntimeException {

    public ApiRequestCurrencyException(String message) {
        super(message);
    }

    public ApiRequestCurrencyException(String message, Throwable cause) {
        super(message, cause);
    }

}
