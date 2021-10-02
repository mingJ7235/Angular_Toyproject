package com.rest.angular_api.advice.exception;

public class CAuthenticationEntryPointException extends RuntimeException{

    public CAuthenticationEntryPointException(String message, Throwable cause) {
        super(message, cause);
    }

    public CAuthenticationEntryPointException(String message) {
        super(message);
    }

    public CAuthenticationEntryPointException() {
        super();
    }

}
