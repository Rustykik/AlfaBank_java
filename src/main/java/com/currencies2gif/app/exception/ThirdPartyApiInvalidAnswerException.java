package com.currencies2gif.app.exception;

public class ThirdPartyApiInvalidAnswerException extends RuntimeException {

    public ThirdPartyApiInvalidAnswerException(String message) {
        super(message);
    }

    public ThirdPartyApiInvalidAnswerException(String message, Throwable cause) {
        super(message, cause);
    }
}
